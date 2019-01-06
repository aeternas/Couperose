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
    (is (= parsedLanguage expectedTatarLang))))
