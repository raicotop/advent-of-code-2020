(ns aoc17.core)

(def demo
  ".#.
..#
###")

(defn read-input
  [s]
  (let [in (->> (clojure.string/split-lines s)
                (map (fn [row] (map {\. false \# true} row))))]
    (->> (for [x (range (count in))
               y (range (count (first in)))]
           {:x x :y y :z 0})
         (filter #(= true (nth (nth in (:x %)) (:y %))))
         (set))))
       
(defn neighbors
  [{x :x y :y z :z} field]
  (->> (for [x-loc [(dec x) x (inc x)]
             y-loc [(dec y) y (inc y)]
             z-loc [(dec z) z (inc z)]
             :when (or (not= x x-loc)
                       (not= y y-loc)
                       (not= z z-loc))]
         {:x x-loc :y y-loc :z z-loc})
       (filter #(contains? field %))
       (count)))

(defn new-generation
  [field]
  (let [x-min (reduce min (map :x field))
        x-max (reduce max (map :x field))
        y-min (reduce min (map :y field))
        y-max (reduce max (map :y field))
        z-min (reduce min (map :z field))
        z-max (reduce max (map :z field))]
    (->> (for [x (range (dec x-min) (+ 2 x-max))
               y (range (dec y-min) (+ 2 y-max))
               z (range (dec z-min) (+ 2 z-max))]
           {:x x :y y :z z})
         (filter (fn [point]
                   (let [neighs (neighbors point field)]
                     (if (contains? field point)
                       (<= 2 neighs 3)
                       (= 3 neighs)))))
         (set))))   

(defn sol-1
  [s]
  (loop [n 0
         field (read-input s)]
    (if (= 6 n)
      (count field)
      (recur (inc n) (new-generation field)))))

(defn read-input-2
  [s]
  (let [in (->> (clojure.string/split-lines s)
                (map (fn [row] (map {\. false \# true} row))))]
    (->> (for [x (range (count in))
               y (range (count (first in)))]
           {:x x :y y :z 0 :w 0})
         (filter #(= true (nth (nth in (:x %)) (:y %))))
         (set))))

(defn neighbors-2
  [{x :x y :y z :z w :w} field]
  (->> (for [x-loc [(dec x) x (inc x)]
             y-loc [(dec y) y (inc y)]
             z-loc [(dec z) z (inc z)]
             w-loc [(dec w) w (inc w)]
             :when (or (not= x x-loc)
                       (not= y y-loc)
                       (not= z z-loc)
                       (not= w w-loc))]
         {:x x-loc :y y-loc :z z-loc :w w-loc})
       (filter #(contains? field %))
       (count)))

(defn new-generation-2
  [field]
  (let [x-min (reduce min (map :x field))
        x-max (reduce max (map :x field))
        y-min (reduce min (map :y field))
        y-max (reduce max (map :y field))
        z-min (reduce min (map :z field))
        z-max (reduce max (map :z field))
        w-min (reduce min (map :w field))
        w-max (reduce max (map :w field))]
    (->> (for [x (range (dec x-min) (+ 2 x-max))
               y (range (dec y-min) (+ 2 y-max))
               z (range (dec z-min) (+ 2 z-max))
               w (range (dec w-min) (+ 2 w-max))]
           {:x x :y y :z z :w w})
         (filter (fn [point]
                   (let [neighs (neighbors-2 point field)]
                     (if (contains? field point)
                       (<= 2 neighs 3)
                       (= 3 neighs)))))
         (set))))

(defn sol-2
  [s]
  (loop [n 0
         field (read-input-2 s)]
    (if (= 6 n)
      (count field)
      (recur (inc n) (new-generation-2 field)))))

(comment
  (sol-1 demo)
  (sol-1 (slurp "resources/input.txt")) ;=> 247

  (sol-2 demo)
  (sol-2 (slurp "resources/input.txt")) ;=> 1392
  ,)
