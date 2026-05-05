# Air Quality Analysis & Linear Regression

## Libraries

```python
import pandas as pd
import numpy as np
```

---

## Part 1: Handling Missing Values

### Loading & Inspecting Data

```python
air = pd.read_csv('airquality.csv')

air.shape       # (rows, columns)
air.head()      # first 5 rows
air.info()      # data types and non-null counts
air.describe()  # summary statistics for numeric columns
```

### Checking for Missing Values

```python
air.count()        # count of non-missing values per column
air.isnull().sum() # count of missing values per column
```

---

### Strategy 1 — Drop Rows with Missing Values

```python
A = air.dropna()
A.shape  # (111, 7)
```

---

### Strategy 2 — Fill with a Constant

```python
A = air.fillna(0)
A.shape  # (153, 7)
A.head()
```

---

### Strategy 3 — Forward Fill

> Propagates the **last valid value forward** to fill gaps.

```python
A = air.fillna(method='pad')   # 'pad' is an alias for forward fill
A.head()
```

---

### Strategy 4 — Backward Fill

> Propagates the **next valid value backward** to fill gaps.

```python
A = air.fillna(method='bfill')
A.head()
```

---

### Strategy 5 — Column-Level Imputation (Ozone)

> Replace missing values in a single column using a statistical measure.

```python
# Replace with mean
A = air['Ozone'].replace(np.nan, air['Ozone'].mean())
A.head()

# Replace with median
A = air['Ozone'].replace(np.nan, air['Ozone'].median())
A.head()

# Replace with mode
A = air['Ozone'].replace(np.nan, air['Ozone'].mode())
A.head()
```

# Remove outliers from the Ozone column using IQR
Q1 = air['Ozone'].quantile(0.25)
Q3 = air['Ozone'].quantile(0.75)
IQR = Q3 - Q1

air = air[(air['Ozone'] >= Q1 - 1.5 * IQR) & (air['Ozone'] <= Q3 + 1.5 * IQR)]

print(f"Rows after outlier removal: {air.shape[0]}")


---

## Part 2: Model Building — Linear Regression

### Additional Libraries

```python
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error, r2_score
import matplotlib.pyplot as plt
from sklearn.preprocessing import MinMaxScaler
```

---

### Loading & Preprocessing

```python
df = pd.read_csv('airquality.csv')

# Fill missing values with forward fill, then backward fill for any remaining gaps
df = df.fillna(method='ffill').fillna(method='bfill')
```

---

### Defining Features & Target

```python
X = df.drop('Ozone', axis=1)  # features (all columns except Ozone)
y = df['Ozone']               # target variable
```

---

### Feature Scaling

> `StandardScaler` standardizes features to zero mean and unit variance — important for regression models.

```python
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)
X_scaled.shape
X_scaled.head()

minmaxScaler = MinMaxScaler()
X_scaled_min_max = minmaxScaler.fit_transform(X)
X_scaled_min_max.shape
X_scaled_min_max.head()

---

### Train-Test Split

```python
X_train, X_test, y_train, y_test = train_test_split(
    X_scaled, y,
    test_size=0.2,    # 80% train, 20% test
    random_state=42
)
```

---

### Training the Model

```python
model = LinearRegression()
model.fit(X_train, y_train)
```

---

### Making Predictions

```python
y_pred = model.predict(X_test)
```

---

### Evaluating the Model

```python
mse = mean_squared_error(y_test, y_pred)
r2  = r2_score(y_test, y_pred)

print(f'Mean Squared Error: {mse:.2f}')
print(f'R² Score:           {r2:.2f}')
```

| Metric | Description |
|---|---|
| **MSE** | Average squared difference between actual and predicted values — lower is better |
| **R² Score** | Proportion of variance explained by the model — closer to 1.0 is better |

---

### Visualizing Predictions vs Actual

```python
plt.scatter(y_test, y_pred)
plt.xlabel('Actual Ozone')
plt.ylabel('Predicted Ozone')
plt.title('Actual vs Predicted Ozone Levels')
plt.plot([y.min(), y.max()], [y.min(), y.max()], 'r--')  # perfect prediction line
plt.show()
```


## simpleImputer — Advanced Imputation
```python
from sklearn.impute import SimpleImputer
imputer = SimpleImputer(strategy='mean')  # can also use 'median', 'most_frequent', or 'constant'
X_imputed = imputer.fit_transform(X)
X_imputed.shape
X_imputed.head()
```