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
    (def expectedTatarLang (dtos/make-language "Tatar" "tt"))
    (defn tatarLang
      []
      (def tatarLangJson (json/read-str "{\"fullName\":\"Tatar\", \"code\":\"tt\"}"
                                      :key-fn keyword))
      (dtos/make-language (:fullName tatarLangJson) (:code tatarLangJson)))
    (is (= (tatarLang) expectedTatarLang))))
