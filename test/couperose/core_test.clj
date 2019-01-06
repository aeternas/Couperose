(ns couperose.core-test
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [couperose.core :refer :all]
            [couperose.parsers.language :as languageParser]
            [couperose.dto.dtos :as dtos]))

(deftest LanguageParsingTest
  (def expectedTatarLang (dtos/make-language "Tatar" "tt"))
  (def jsonString "{\"fullName\":\"Tatar\", \"code\":\"tt\"}")
  (testing "parsing with parser"
    (def parsedLanguage (languageParser/parseLanguage jsonString))
    (is (= parsedLanguage expectedTatarLang)))

  (testing "parsing from json string"
    (defn tatarLang
      []
      (def tatarLangJson (json/read-str jsonString
                                        :key-fn keyword))
      (dtos/make-language (:fullName tatarLangJson) (:code tatarLangJson)))
    (is (= (tatarLang) expectedTatarLang))))
