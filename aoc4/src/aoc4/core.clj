(ns aoc4.core
  (:require clojure.set))

(defn valid1? 
  [m]
  (clojure.set/subset?
    #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"}
    (-> m keys set)))

(defn valid2?
  [m]
  (and (<= 1920 (read-string (m "byr")) 2002)
       (<= 2010 (read-string (m "iyr")) 2020)
       (<= 2020 (read-string (m "eyr")) 2030)
       (<= 3 (count (m "hgt")))
       (let [hgt (m "hgt")
             n (read-string (apply str (drop-last 2 hgt)))
             u (apply str (take-last 2 hgt))]
         (cond 
           (= "cm" u) (<= 150 n 193)
           (= "in" u) (<= 59 n 76)
           :else false))
       (let [hcl (m "hcl")]
         (= hcl (re-matches #"#[0-9a-f]{6}" hcl)))
       (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} (m "ecl"))
       (let [pid (m "pid")]
         (= pid (re-matches #"[0-9]{9}" pid)))))
             
(defn sol
  [s second-filter?]
  (->> (clojure.string/split s #"\n\n")
       (map #(clojure.string/replace % #"[\n:]" " "))
       (map #(clojure.string/split % #" "))
       (map #(apply hash-map %))
       (filter valid1?)
       (filter second-filter?)
       (count)))

(defn sol1
  [s]
  (sol s identity))

(defn sol2
  [s]
  (sol s valid2?))

(comment
  (sol1 (slurp "resources/input.txt")) ;=> 228
  (sol2 (slurp "resources/input.txt")));=> 175