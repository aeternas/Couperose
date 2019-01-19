(ns couperose.services.retriever
  (:require [clojure.data.json :as json]
            [clj-http.client :as client]
            [clojure.java.io :as io]
            [couperose.parsers.translation :as translationParser]
            [couperose.parsers.language :as languageParser]))

(def hostname (System/getenv "HOSTNAME"))
(def groupsUrl (str "https://" hostname "/v1/groups"))
(def baseUrl (str "https://" hostname "/v1/"))

(defn sendRequests
  [phrases languageGroups]
  (println (map (fn [phrase] (client/get (str baseUrl (translationParser/getQuery phrase languageGroups))))
                phrases)))

(defn getPhrases
  []
  (with-open [rdr (clojure.java.io/reader (io/resource "file.txt"))]
    (reduce conj [] (line-seq rdr))))

(defn retrieveGroups
  []
  (client/get groupsUrl
            {:async? true}
            (fn [response] (let [languagesArray (languageParser/parseLanguageGroupArray (:body response))]
                                                              (sendRequests (getPhrases) languagesArray)))
            (fn [exception] (println "exception message is: " (.getMessage exception)))))
