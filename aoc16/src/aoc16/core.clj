(ns aoc16.core)

(def demo 
  "class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12")

(defn read-values 
  [s]
  (->> (clojure.string/split-lines s)
       (partition-by #{""})
       (remove #{(list "")})
       (map #(remove #{"your ticket:"} %))
       (map #(remove #{"nearby tickets:"} %))
       (map vec)
       (vec)))

(defn preprocess
  [[rules [your-ticket] nearby-tickets]]
  (let [rules-processed (vec (map #(clojure.string/split % #"(: )|( or )|(-)") rules))
        your-ticket-processed (clojure.string/split your-ticket #",")
        nearby-tickets-processed (vec (map #(clojure.string/split % #",") nearby-tickets))]
    [rules-processed your-ticket-processed nearby-tickets-processed]))

(defn sol-1
  [s]
  (->> (read-values s)
       (preprocess)))

(comment
  
  (sol-1 demo)
  
  ,)