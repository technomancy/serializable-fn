(ns string-fn.core-test
  (:refer-clojure :exclude [fn])
  (:use [string-fn.core] :reload-all)
  (:use [clojure.test]))

(def dinc-list '(fn [x] (inc (inc x))))

(def dinc (eval dinc-list))

(deftest fns-created-with-metadata
  (is (ifn? dinc))
  (is (map? (meta dinc))))

(deftest metadata-fns-work-as-original
  (is (= 2 (dinc 0))))

(deftest metadata-fns-return-source
  (is (= dinc-list (:string-fn.core/source (meta dinc)))))

(deftest printing-fns-show-source
  (is (= (pr-str dinc-list)
         (pr-str dinc))))

(deftest preserve-reader-metadata
  (is (number? (:line (meta (:string-fn.core/source
                             (meta dinc)))))))

(deftest serializable-fn-roundtrip!!!111eleven
  (is (= 2 ((eval (read-string (pr-str dinc))) 0))))
