# Assignment 3: Pandas Subsets

## Libraries

```python
import pandas as pd
import numpy as np
```

---

## Loading & Inspecting Data

```python
data = pd.read_csv('data.csv')

print(type(data))   # <class 'pandas.core.frame.DataFrame'>
data.shape          # (rows, columns)
data.head()         # first 5 rows
data.tail()         # last 5 rows
```

---

## `iloc` — Index-Based Selection

> **Syntax:** `data.iloc[rows, cols]` — uses integer positions, end index is **exclusive**.

### Row & Column Slices

```python
data.iloc[2:10, :]      # rows 2–9, all columns
data.iloc[2:10, 1:5]    # rows 2–9, columns 1–4
data.iloc[2:10, 0]      # rows 2–9, column 0 only
data.iloc[:10, :5]      # rows 0–9, columns 0–4
data.iloc[12:, 10:]     # row 12 to end, column 10 to end
data.iloc[12, :]        # single row 12 (returns a Series)
data.iloc[:, 6]         # all rows, column 6
data.iloc[:, 18]        # all rows, column 18
data.iloc[12:20, 18]    # rows 12–19, column 18
data.iloc[:, :-12]      # all rows, drop last 12 columns
```

### Fancy Indexing (Lists of Positions)

```python
data.iloc[12:20, [2, 5, 11, 13]]         # rows 12–19, specific columns
data.iloc[[1, 2, 6, 88, 12], [2, 5, 11, 13]]  # specific rows & columns
```

### Non-Contiguous Row Ranges

```python
x = []
x.extend(range(2, 21))   # rows 2–20
x.extend(range(50, 70))  # rows 50–69

data.iloc[x, :]           # rows 2–20 and 50–69, all columns
data.iloc[x, 1:5]         # same rows, columns 1–4
```

### Storing a Subset

```python
X = data.iloc[2:10, 1:5]
X.shape  # (8, 4)
```

---

## `loc` — Label-Based Selection

> **Syntax:** `data.loc[rows, cols]` — uses column names/labels, end index is **inclusive**.

```python
data.loc[:, 'Type']                             # all rows, column 'Type'
data.loc[:, ['Type', 'like', 'comment']]        # all rows, named columns
data.loc[100:110, ['Type', 'like', 'comment']]  # rows 100–110, named columns
```

### Shorthand Column Selection

```python
data[['Type', 'like', 'comment']]  # equivalent to loc with column list

data.columns  # view all column names
```

---

## Boolean Filtering

```python
X = data[['Type', 'Category', 'comment', 'like', 'share']]
X.shape

# Single condition
Z = X[X['like'] > 100]
Z.shape
Z.head()

# Multiple conditions — wrap each in () and combine with &
Z = X[(X['like'] > 100) & (X['share'] > 40)]
Z.shape
Z.head()
```

---

## Exporting Data

```python
Z.to_csv('output.csv', index=False)    # save as CSV
Z.to_html('output.html', index=False)  # save as HTML table
```

---

## Merging DataFrames

> **Note:** `DataFrame.append()` was removed in pandas 2.0. Use `pd.concat()` instead.

```python
new_data = pd.read_csv('newfb.csv')
new_data.shape  # (134, 19)
data.shape      # (500, 19)

merged = pd.concat([data, new_data], ignore_index=True)
merged.shape    # (634, 19)
```

---

## Dropping Rows & Columns

```python
# Drop columns (axis=1)
Y = data.drop(['Type', 'comment', 'Category'], axis=1)
Y.shape  # (500, 16)

# Drop rows by index (axis=0 is default)
Y = data.drop([1, 2, 3, 4, 5, 6, 7, 8, 9])
Y.shape  # (491, 19)
```

---

## Sorting & Transposing

```python
# Sort by column value
Y = Z.sort_values(by='comment', ascending=False)
Y.head()

# Transpose — swap rows and columns
Y = Z.T
Y.shape  # (5, 84)
```

---

## `melt` — Wide to Long Format

```python
Z.melt()
Z.melt(id_vars=['Type'])
Z.melt(id_vars=['Type', 'Category'])
Z.melt(id_vars=['Type', 'Category'], value_vars=['like', 'share'])
```

---

## One-Hot Encoding with `get_dummies`

> Converts categorical columns into binary (0/1) dummy columns — one per unique category value.

```python
Y = pd.get_dummies(Z)
Y.shape  # (41, 28) — more columns due to dummy expansion
```

---

## Introspection

```python
dir(Z)        # list all attributes and methods on Z
len(dir(Z))   # count them
```