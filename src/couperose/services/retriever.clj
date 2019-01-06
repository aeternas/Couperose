(ns couperose.services.retriever
  (:require [clj-http.client :as client]))

(def hostname (System/getenv "HOSTNAME"))
(def url (str "https://" hostname "/v1/groups")) 

(defn retrieve
  []
  (client/get url
            {:async? true}
            ;; respond callback
            (fn [response] (println "response is:" (get response :body)))
            ;; raise callback
            (fn [exception] (println "exception message is: " (.getMessage exception)))))
