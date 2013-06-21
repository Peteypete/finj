(ns com.github.sebhoss.finj.annuity-test
  (:require [com.github.sebhoss.finj.annuity :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest present-immediate-value-test
  (testing "with positive integers"
    (is (≈ 432 (present-immediate-value :payment 100 :rate 0.05 :period 5)))))

(deftest present-due-value-test
  (testing "with positive integers"
    (is (≈ 454 (present-due-value :payment 100 :rate 0.05 :period 5)))))

(deftest future-immediate-value-test
  (testing "with positive integers"
    (is (≈ 552 (future-immediate-value :payment 100 :rate 0.05 :period 5)))))

(deftest future-due-value-test
  (testing "with positive integers"
    (is (≈ 580 (future-due-value :payment 100 :rate 0.05 :period 5)))))

(deftest perpetuity-immediate-value-test
  (testing "with positive integers"
    (is (≈ 2000 (perpetuity-immediate-value :payment 100 :rate 0.05)))))

(deftest perpetuity-due-value-test
  (testing "with positive integers"
    (is (≈ 2100 (perpetuity-due-value :payment 100 :rate 0.05)))))