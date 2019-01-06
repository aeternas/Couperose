(ns couperose.dto.dtos)

(defrecord Language [fullName code]) 

(defrecord LanguageGroup [name languages])

(defn make-language ([fullName code]
                     (->Language fullName code)))

(defn make-language-group ([name languages]
                           (->LanguageGroup name languages)))
