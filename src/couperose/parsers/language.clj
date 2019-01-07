(ns couperose.parsers.language
  (:require [clojure.data.json :as json]
            [couperose.dto.dtos :as dtos]))

(defn parseLanguage
  [data]
  (def jsonObject (json/read-str data
                                 :key-fn keyword))
  (dtos/make-language (:fullName jsonObject) (:code jsonObject)))

(defn parseLanguageArray
  [data]
  (def jsonObject (json/read-str data
                                 :key-fn keyword))
  (into [] (map #(dtos/make-language (:fullName %) (:code %)) jsonObject)))
