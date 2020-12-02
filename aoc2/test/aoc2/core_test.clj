(ns aoc2.core-test
  (:require [clojure.test :refer :all]
            [aoc2.core :refer :all]))

(def task
  "1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc")

(deftest a-test
  (testing "validation of parsed line"
    (is (= true (valid1? 1 3 \a "abcde")))
    (is (= false (valid1? 1 3 \b "cdefg")))
    (is (= true (valid1? 2 9 \c "ccccccccc"))))
  (testing "line parsing"
    (is (= [1 3 \a "abcde"] (parse-line "1-3 a: abcde")))
    (is (= [2 9 \c "ccccccccc"] (parse-line "2-9 c: ccccccccc"))))
  (testing "valid solution"
    (is (= 2 (sol1 task)))
    (is (= 600 (sol1 (slurp "input.txt")))))
  (testing "validation of parsed line (second part)"
    (is (= true (valid2? 1 3 \a "abcde")))
    (is (= false (valid2? 1 3 \b "cdefg")))
    (is (= false (valid2? 2 9 \c "ccccccccc"))))
  (testing "valid solution (second part)"
    (is (= 1 (sol2 task)))
    (is (= 245 (sol2 (slurp "input.txt"))))))

