(ns couperose.services.cacheWarmer
  (:require [clojure.data.json :as json]
            [clj-http.client :as client]
            [clojure.java.io :as io]
            [couperose.parsers.translation :as translationParser]
            [couperose.parsers.language :as languageParser]))

(def hostname (System/getenv "HOSTNAME"))
(def baseUrl (str "https://" hostname "/v1/"))
(def groupsUrl (str baseUrl "groups"))

(defn translatePhraseLanguageGroups
  [phrase languageGroups]
  (let [groupPhraseTranslationQuery (translationParser/getQuery phrase languageGroups)]
    (let [groupPhraseTranslationUrl (str baseUrl groupPhraseTranslationQuery)]
      (client/get groupPhraseTranslationUrl))))

(defn sendRequests
  [phrases languageGroups]
      (println (map #(translatePhraseLanguageGroups languageGroups) phrases)))

(defn getBaseLexems
  [filename]
  (with-open [rdr (clojure.java.io/reader (io/resource filename))]
    (reduce conj [] (line-seq rdr))))

(defn warmCache
  []
  (let [groups (future (client/get groupsUrl))]  
    (let [languagesArray (languageParser/parseLanguageGroupArray (:body @groups))]
      (sendRequests (getBaseLexems "words/words") languagesArray))))
