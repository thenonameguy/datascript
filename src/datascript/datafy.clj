(ns datascript.datafy
  (:require [clojure.core.protocols :as p]
            [datascript
             [core :as d]
             [db :as db]])
  (:import [datascript.db Datom DB]
           datascript.impl.entity.Entity))

(extend-protocol p/Datafiable
  DB
  (datafy [db]
    {:schema   (db/-schema db)
     :entities (into #{}
                     (comp (map :e)
                           (distinct)
                           (map #(d/entity db %)))
                     (d/datoms db :eavt))
     :datoms   (seq db)})
  Datom
  (datafy [d]
    {:e (.-e d)
     :a (.-a d)
     :v (.-v d)
     :t (db/datom-tx d)
     :x (db/datom-added d)})
  Entity
  (datafy [e]
    (into {} (d/touch e))))
