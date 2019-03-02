(ns couperose.services.cacheWarmer
  (:require [clojure.data.json :as json]
            [clj-http.client :as client]
            [clojure.java.io :as io]
            [couperose.parsers.translation :as translationParser]
            [couperose.parsers.language :as languageParser]))

(def hostname (System/getenv "HOSTNAME"))
(def baseUrl (str "https://" hostname "/v1/"))
(def groupsUrl (str baseUrl "groups"))

(defn sendRequests
  [phrases languageGroups]
  (println (map (fn [phrase] (client/get (str baseUrl (translationParser/getQuery phrase languageGroups))))
                phrases)))

(defn getBaseLexems
  [filename]
  (with-open [rdr (clojure.java.io/reader (io/resource filename))]
    (reduce conj [] (line-seq rdr))))

(def groups
  (future
    (client/get groupsUrl)))

(defn warmCache
  []
  (let [languagesArray (languageParser/parseLanguageGroupArray (:body @groups))]
    (sendRequests (getBaseLexems "words") languagesArray)))
