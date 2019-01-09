(ns couperose.parsers.translation)

(defn getTranslationQuery
  [phrases languageGroups]
  )

(defn getLanguageGroupsQuery
  [languageGroups]
  (let [groupNames (map #(:name %) languageGroups)]
      (str "&group=" (clojure.string/join "&group=" groupNames))))
