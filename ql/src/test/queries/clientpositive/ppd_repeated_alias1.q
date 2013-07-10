-- see HIVE-1342

drop table pokes;
drop table pokes2;
create table pokes (foo int, bar int);
create table pokes2 (foo int, bar int);

-- Q1: predicate should not be pushed on the right side of a left outer join
explain
SELECT a.foo as foo1, b.foo as foo2, b.bar
FROM pokes a LEFT OUTER JOIN pokes2 b
ON a.foo=b.foo
WHERE b.bar=3;

-- Q2: predicate should not be pushed on the right side of a left outer join
explain
SELECT * FROM
    (SELECT a.foo as foo1, b.foo as foo2, b.bar
    FROM pokes a LEFT OUTER JOIN pokes2 b
    ON a.foo=b.foo) a
WHERE a.bar=3;

-- Q3: predicate should be pushed
explain
SELECT * FROM
    (SELECT a.foo as foo1, b.foo as foo2, a.bar
    FROM pokes a JOIN pokes2 b
    ON a.foo=b.foo) a
WHERE a.bar=3;

drop table pokes;
drop table pokes2;