#||
Danh Nguyen, Ryan Dehart, Teresa Condon
Program 3 -Word Search
2 November 2016
Res:
http://stackoverflow.com/questions/30599229/dynamic-2d-array-in-lisp
||#

(format t "Back so soon, Churl? Here is your puzzle ~%") ;greeting

;;; ---------- GLOBAL VARS ----------
(defconstant LEFT 0)
(defconstant RIGHT 1)
(defconstant UP 2)
(defconstant DOWN 3)
(defvar *numRows* 16)
(defvar *numCols* 16)
(setf *random-state* (make-random-state t)) ;seed for random

;; This is the alphabet array
(defparameter alphabet (make-array 26))
(setf (aref alphabet 0) 'a)
(setf (aref alphabet 1) 'b)
(setf (aref alphabet 2) 'c)
(setf (aref alphabet 3) 'd)
(setf (aref alphabet 4) 'e)
(setf (aref alphabet 5) 'f)
(setf (aref alphabet 6) 'g)
(setf (aref alphabet 7) 'h)
(setf (aref alphabet 8) 'i)
(setf (aref alphabet 9) 'j)
(setf (aref alphabet 10) 'k)
(setf (aref alphabet 11) 'l)
(setf (aref alphabet 12) 'm)
(setf (aref alphabet 13) 'n)
(setf (aref alphabet 14) 'o)
(setf (aref alphabet 15) 'p)
(setf (aref alphabet 16) 'q)
(setf (aref alphabet 17) 'r)
(setf (aref alphabet 18) 's)
(setf (aref alphabet 19) '"T") ;the letter t is a reserved variable in LISP
(setf (aref alphabet 20) 'u)
(setf (aref alphabet 21) 'v)
(setf (aref alphabet 22) 'w)
(setf (aref alphabet 23) 'x)
(setf (aref alphabet 24) 'y)
(setf (aref alphabet 25) 'z)

;; This is the array containing all the words in the txt file
(defparameter hiddenWords (make-array 5))
(setf (aref hiddenWords 0) 'Catherine)
(setf (aref hiddenWords 1) 'Applesauce)
(setf (aref hiddenWords 2) 'Scapula)
(setf (aref hiddenWords 3) 'Dirigible)
(setf (aref hiddenWords 4) 'Nullify)

;; This is the game board
(setf board (make-array '(16 16)));end board 2D array

;;; ---------- FUNCTIONS ----------
(defun makeBoard ()
  (dotimes (i 16)
    (dotimes (j 16)
      (setf (aref board i j) (aref alphabet (random 25)))
))) ;end makeBoard

;;; read the words from the txt file into an array and choose random words
(defun setHiddenWords ()
  (with-open-file (stream "dictionary/WSdictionary.txt"
                  :direction :output
                  :if-exists :supersede)
  (loop for line = (read-line stream nil)
    while line do (setf (aref hiddenWords i) line)
  ))
) ;end SetHiddenWords

;;; place the hidden word into the game board
(defun makePuzzle ()
  (dotimes (j 5)
    (defparameter firstLetterPos (make-array 2)) ;coordinate of first letter of word to be put in board
    (defvar numTries 0)
    (defvar leftOpen nil)
    (defvar rightOpen nil)
    (defvar upOpen nil)
    (defvar downOpen nil)
    (loop while (= leftOpen nil)
      do (setf (aref firstLetterPos 0) (random 16)) ;choose a random position in rows
          (setf (aref firstLetterPos 1) (random 16)) ;choose a random position in cols
      ; rightOpen = (numCols - firstLetterPos[1] >= hiddenwrds[j].length());
      ; leftOpen = (firstLetterPos[1] >= hiddenwrds[j].length());
      ; upOpen = (firstLetterPos[0] >= hiddenwrds[j].length());
      ; downOpen = (numRows - firstLetterPos[0] >= hiddenwrds[j].length());
      (format t "Hello")
    )
  )
)

;;; ---------- WRITE AND PRINT TXT FILE ----------
;;;write puzzle file
(defun PrintPuzzle ()
(with-open-file (my-stream "puzzle.txt"
                  :direction :output
                  :if-exists :supersede)
            (princ board my-stream))

;;;Open puzzle file
(let ((in (open "puzzle.txt" :if-does-not-exist nil)))
  (when in
    (loop for line = (read-line in nil)
    while line do (format t "~a~%" line))
  (close in)
)));end PrintPuzzle


;;; ---------- MAIN ----------
;;;Acts as main function, calling all functions
(defun runFunc ()
  (makeBoard)
  (PrintPuzzle)
)
(runFunc)
