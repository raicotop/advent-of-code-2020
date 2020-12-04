(ns aoc4.core-test
  (:require [clojure.test :refer :all]
            [aoc4.core :refer :all]))

(def task
  "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in")

(def valid-solution
  {"pid" "087499704" 
   "hgt" "74in" 
   "ecl" "grn" 
   "iyr" "2012" 
   "eyr" "2030" 
   "byr" "1980" 
   "hcl" "#623a2f"})

(deftest solutions
  (testing "first part"
    (is (= 2 (sol1 task)))
    (is (= 228 (sol1 (slurp "resources/input.txt")))))
  (testing "second part validation"
    (is (= true (valid2? valid-solution)))
    (is (= false (valid2? (assoc valid-solution "byr" "1919"))))
    (is (= false (valid2? (assoc valid-solution "byr" "2003"))))
    (is (= false (valid2? (assoc valid-solution "iyr" "2009"))))
    (is (= false (valid2? (assoc valid-solution "iyr" "2021"))))
    (is (= false (valid2? (assoc valid-solution "eyr" "2019"))))
    (is (= false (valid2? (assoc valid-solution "eyr" "2031"))))
    (is (= false (valid2? (assoc valid-solution "hgt" "58in"))))
    (is (= false (valid2? (assoc valid-solution "hgt" "77in"))))
    (is (= false (valid2? (assoc valid-solution "hgt" "149cm"))))
    (is (= false (valid2? (assoc valid-solution "hgt" "194cm"))))
    (is (= false (valid2? (assoc valid-solution "hgt" "194"))))
    (is (= false (valid2? (assoc valid-solution "hcl" "#123abz"))))
    (is (= false (valid2? (assoc valid-solution "hcl" "123abz"))))
    (is (= false (valid2? (assoc valid-solution "ecl" "wat"))))
    (is (= false (valid2? (assoc valid-solution "pid" "0123456789"))))))
