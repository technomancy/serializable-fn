(ns serializable.fn
  "Serializable functions! Check it out."
  (:refer-clojure :exclude [fn]))

(defn- save-env [env form]
  (if env
    `(let ~(vec (flatten (for [[name local] env]
                         [name (.v (.init local))])))
       (~@form))
    form))

(defmacro ^{:doc (str (:doc (meta #'clojure.core/fn))
                      "\n\n  Oh, but it also allows serialization!!!111eleven")}
  fn [& sigs]
  `(with-meta (clojure.core/fn ~@sigs)
     {:type ::serializable-fn
      ::source (quote ~(save-env &env &form))}))

(defmethod print-method ::serializable-fn [o ^Writer w]
  (print-method (::source (meta o)) w))
