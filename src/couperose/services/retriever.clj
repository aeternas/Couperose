(ns couperose.services.retriever
  (:require [clojure.data.json :as json]
            [clj-http.client :as client]
            [couperose.parsers.language :as languageParser]))

(def hostname (System/getenv "HOSTNAME"))
(def url (str "https://" hostname "/v1/groups")) 

(defn retrieveGroups
  []
  (client/get url
            {:async? true}
            (fn [response] (let [languagesArray (languageParser/parseLanguageGroupArray (:body response))]
                                                              (println languagesArray)))
            (fn [exception] (println "exception message is: " (.getMessage exception)))))
