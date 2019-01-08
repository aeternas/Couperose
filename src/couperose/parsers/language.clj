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

(defn makeLanguageGroup
  [json]
  (dtos/make-language-group
    (:name json)
    (into [] (map makeLanguage (:languages json)))))

(defn parseLanguage
  [data]
  (makeLanguage (getJson data)))

(defn parseLanguageArray
  [data]
  (into [] (map makeLanguage (getJson data))))

(defn parseLanguageGroup
  [data]
  (makeLanguageGroup (getJson data)))

(defn parseLanguageGroupArray
  [data]
  (into [] (map makeLanguageGroup (getJson data))))
