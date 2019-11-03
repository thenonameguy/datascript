(ns datascript.datafy-test
  (:require [clojure.datafy :refer [datafy]]
            [clojure.test :as t]
            [datascript.core :as dc]
            [datascript.datafy]))

(t/deftest database
  (t/is (= {:schema   nil
            :entities #{}
            :datoms   nil}
           (datafy (dc/empty-db)))))

(t/deftest datom
  (t/is (= {:e 1
            :a :foo/bar
            :v 42
            :t 93939
            :x true}
           (datafy (dc/datom 1 :foo/bar 42 93939 true)))))

(def some-db (dc/db-with
              (dc/empty-db)
              [{:foo/bar 420
                :bar/foo 69
                :db/id   1}]))

(t/deftest entity
  (t/is (= {:bar/foo 69
            :foo/bar 420}
           (datafy (dc/entity some-db 1)))))
