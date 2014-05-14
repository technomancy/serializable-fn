(ns serializable.fn
  "Serializable functions! Check it out."
  (:refer-clojure :exclude [fn])
  (:import java.io.Writer))

(defn- symbols [sexp]
  "Returns just the symbols from the expression, including those
   inside literals (sets, maps, lists, vectors)."
  (distinct (filter symbol? (tree-seq coll? seq sexp))))

(defn- save-env [env form]
  (let [used-locals (keys (select-keys env (symbols form)))
        form (with-meta (cons `fn (rest form)) ; serializable/fn, not core/fn
                              (meta form))
        quoted-form `(quote ~form)]
    (if (seq used-locals)
      `(list `let [~@(for [local used-locals,
                           let-arg [`(quote ~local)
                                    `(list `quote ~local)]]
                       let-arg)]
             ~quoted-form)
      quoted-form)))

(defmacro ^{:doc (str (:doc (meta #'clojure.core/fn))
                      "\n\n  Oh, but it also allows serialization!!!111eleven")}
  fn [& sigs]
  `(with-meta (clojure.core/fn ~@sigs)
     {:type ::serializable-fn
      ::source ~(save-env &env &form)}))

(defmethod print-method ::serializable-fn [o ^Writer w]
  (print-method (::source (meta o)) w))
