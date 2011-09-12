(ns serializable.fn
  "Serializable functions! Check it out."
  (:refer-clojure :exclude [fn]))

(defn- save-env [bindings form]
  (if bindings
    `(let ~(vec (apply concat (for [b bindings]
                                [(.sym b) (.eval (.init b))])))
       (~@form))
    form))

(defmacro ^{:doc (str (:doc (meta #'clojure.core/fn))
                      "\n\n  Oh, but it also allows serialization!!!111eleven")}
  fn [& sigs]
  `(with-meta (clojure.core/fn ~@sigs)
     {:type ::serializable-fn
      ::source (quote ~(save-env (vals &env) &form))}))

(defmethod print-method ::serializable-fn [o ^Writer w]
  (print-method (::source (meta o)) w))
