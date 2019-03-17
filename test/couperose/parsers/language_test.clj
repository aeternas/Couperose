(ns couperose.parsers.language-test
  (:require [clojure.test :refer :all]
            [couperose.parsers.language :as languageParser]
            [couperose.dto.dtos :as dtos]))

(deftest parseLanguage
  (def expectedTatarLang (dtos/make-language "Tatar" "tt"))
  (def expectedTurkishLang (dtos/make-language "Turkish" "tr"))
  (def jsonString "{\"fullName\":\"Tatar\", \"code\":\"tt\"}")
  (def arrayJsonString "[{\"fullName\":\"Tatar\", \"code\":\"tt\"}, {\"fullName\":\"Turkish\", \"code\":\"tr\"}]")
  (def languageGroupJsonString "{\"languages\": [{\"fullName\":\"Tatar\", \"code\":\"tt\"}, {\"fullName\":\"Turkish\", \"code\":\"tr\"}], \"name\": \"Turkic\"}")
  (def languageGroupArrayJsonString "[{\"languages\": [{\"fullName\":\"Latvian\", \"code\":\"lv\"}, {\"fullName\":\"Lietuvan\", \"code\":\"lt\"}], \"name\": \"Baltic\"}, {\"languages\": [{\"fullName\":\"Tatar\", \"code\":\"tt\"}, {\"fullName\":\"Turkish\", \"code\":\"tr\"}], \"name\": \"Turkic\"}]")
  
  (testing "parsing with parser"
    (def parsedLanguage (languageParser/parseLanguage jsonString))
    (is (= parsedLanguage expectedTatarLang)))
  
  (testing "parse array of languages"
    (def parsedLanguages (languageParser/parseLanguageArray arrayJsonString))
    (is (= expectedTatarLang (parsedLanguages 0)))
    (is (= expectedTurkishLang (parsedLanguages 1)))))

(deftest parseLanguageGroup
  (testing "parse language group"
    (def parsedLanguageGroup (languageParser/parseLanguageGroup languageGroupJsonString))
    (is (= "Turkic" (:name parsedLanguageGroup)))
    (is (= expectedTurkishLang ((:languages parsedLanguageGroup) 1))))

  (testing "parse language group array"
    (def parsedLanguageGroupArray (languageParser/parseLanguageGroupArray languageGroupArrayJsonString))
    (is (= "Lietuvan" (:fullName((:languages (parsedLanguageGroupArray 0)) 1))))))
