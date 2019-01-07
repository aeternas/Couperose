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

(defn parseLanguageGroup
  [data]
  (def jsonObject (json/read-str data
                                 :key-fn keyword))
  (def languageGroupDict {:name (:name jsonObject) :languages (:languages jsonObject)})
  (dtos/make-language-group (:name languageGroupDict) (into [] (map #(dtos/make-language (:fullName %) (:code %)) (:languages languageGroupDict)))))

(defn parseLanguageGroupArray
  [data]
  (def jsonObject (json/read-str data
                                 :key-fn keyword))
  (defn makeLanguageGroup
    [languageGroupDict]
    (dtos/make-language-group (:name languageGroupDict) (into [] (map #(dtos/make-language (:fullName %) (:code %)) (:languages languageGroupDict)))))

  (into [] (map makeLanguageGroup jsonObject)))
