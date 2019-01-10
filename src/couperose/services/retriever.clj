(ns couperose.services.retriever
  (:require [clojure.data.json :as json]
            [clj-http.client :as client]
            [couperose.parsers.translation :as translationParser]
            [couperose.parsers.language :as languageParser]))

(def hostname (System/getenv "HOSTNAME"))
(def groupsUrl (str "https://" hostname "/v1/groups"))
(def baseUrl (str "https://" hostname "/v1/"))

(defn sendRequests
  [phrases languageGroups]
  (println (map (fn [phrase] (client/get (str baseUrl (translationParser/getQuery phrase languageGroups))))
                phrases)))

(defn retrieveGroups
  []
  (client/get groupsUrl
            {:async? true}
            (fn [response] (let [languagesArray (languageParser/parseLanguageGroupArray (:body response))]
                                                              (sendRequests ["Hello" "Mother" "Father" "Hello, world!" "Cat" "Dog" "Nature" "Language" "I" "How are you"] languagesArray)))
            (fn [exception] (println "exception message is: " (.getMessage exception)))))
