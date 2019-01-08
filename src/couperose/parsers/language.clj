(ns couperose.parsers.language
  (:require [clojure.data.json :as json]
            [couperose.dto.dtos :as dtos]))

(defn getJson
  [data]
  (json/read-str data
                 :key-fn keyword))

(defn makeLanguage
  [json]
  (dtos/make-language (:fullName json) (:code json)))

(defn parseLanguage
  [data]
  (def json (getJson data))
  (makeLanguage json))

(defn parseLanguageArray
  [data]
  (def json (getJson data))
  (into [] (map makeLanguage json)))

(defn parseLanguageGroup
  [data]
  (def json (getJson data))
  (dtos/make-language-group
    (:name json)
    (into [] (map makeLanguage (:languages json)))))

(defn parseLanguageGroupArray
  [data]
  (def json (getJson data))
  (defn makeLanguageGroup
    [languageGroupDict]
    (dtos/make-language-group (:name languageGroupDict) (into [] (map #(dtos/make-language (:fullName %) (:code %)) (:languages languageGroupDict)))))

  (into [] (map makeLanguageGroup json)))
