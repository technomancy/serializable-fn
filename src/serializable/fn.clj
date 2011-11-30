(ns serializable.fn
  "Serializable functions! Check it out."
  (:refer-clojure :exclude [fn]))

(defn- save-env [bindings form]
  (if bindings
    `(list `let ~(vec (apply concat (for [b bindings]
                                      [`(quote ~(.sym b))
                                       (.sym b)])))
           '~form)
    `'~form))

(defmacro ^{:doc (str (:doc (meta #'clojure.core/fn))
                      "\n\n  Oh, but it also allows serialization!!!111eleven")}
  fn [& sigs]
  `(with-meta (clojure.core/fn ~@sigs)
     {:type ::serializable-fn
      ::source ~(save-env (vals &env) &form)}))

(defmethod print-method ::serializable-fn [o ^Writer w]
  (print-method (::source (meta o)) w))
