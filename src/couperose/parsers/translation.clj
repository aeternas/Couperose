(ns couperose.parsers.translation)


(defn getLanguageGroupsQuery
  [languageGroups]
  (let [groupNames (map #(:name %) languageGroups)]
      (str "&group=" (clojure.string/join "&group=" groupNames))))

(defn getQuery
  [phrases languageGroups]
  (let [languageGroupsQuery (getLanguageGroupsQuery languageGroups)]
    (str "?translation=" phrases languageGroupsQuery)))
