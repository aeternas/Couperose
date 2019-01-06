(ns couperose.core-test
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [couperose.core :refer :all]
            [couperose.dto.dtos :as dtos]))

(deftest LanguageParsingTest
  (testing "language constructor"
    (def tatarLang (dtos/make-language "Tatar" "tt"))
    (is (= (:fullName tatarLang) "Tatar")))
  (testing "parsing from json string"
    (def jsonString "{\"fullName\":\"Tatar\", \"code\":\"tt\"}")
    (def tatarLangJson (json/read-str jsonString
                                      :key-fn keyword))
    (def tatarLangObj (dtos/make-language (:fullName tatarLangJson) (:code tatarLangJson)))
    (is (= (:fullName tatarLangObj) "Tatar"))
    (is (= (:code tatarLangObj) "tt"))))

