# Python Visualisation

## Libraries

```python
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
```

---

## Part 1: Air Quality Data

```python
df = pd.read_csv('airquality.csv')
```

### Line Plot — Ozone Levels Over Time

```python
air['Ozone'].plot()
plt.title('Ozone Levels Over Time')
plt.xlabel('Observation Index')
plt.ylabel('Ozone Concentration')
plt.show()
```

---

### Pie Chart — Observations by Month

```python
air['Month'].value_counts().plot(kind='pie', autopct='%1.1f%%', startangle=140)
plt.title('Observations by Month')
plt.axis('equal')
plt.show()
```

---

### Scatter Plot — Wind vs Ozone Levels

```python
sns.scatterplot(x='Wind', y='Ozone', data=df)
plt.title('Wind vs Ozone Levels')
plt.xlabel('Wind Speed')
plt.ylabel('Ozone Concentration')
plt.show()
```

---

### Bar Plot — Average Ozone Levels by Month

```python
sns.barplot(x='Month', y='Ozone', data=df)
plt.title('Average Ozone Levels by Month')
plt.xlabel('Month')
plt.ylabel('Average Ozone Concentration')
plt.show()
```

---

### Box Plot — Ozone Levels by Month

```python
sns.boxplot(x='Month', y='Ozone', data=df)
plt.title('Ozone Levels by Month')
plt.xlabel('Month')
plt.ylabel('Ozone Concentration')
plt.show()
```

---

### Histogram — Distribution of Ozone Levels

```python
sns.histplot(df['Ozone'], bins=20, kde=True)
plt.title('Distribution of Ozone Levels')
plt.xlabel('Ozone Concentration')
plt.ylabel('Frequency')
plt.show()
```

---

### Heatmap — Correlation Between Numeric Variables

```python
numeric_cols = df.select_dtypes(include=np.number).columns
corr = df[numeric_cols].corr()
sns.heatmap(corr, annot=True, cmap='coolwarm')
plt.title('Correlation Heatmap')
plt.show()
```

---

## Part 2: Heart Disease Data

```python
heart = pd.read_csv('heart.csv')
```

### Line Plot — Age Distribution

```python
heart['age'].plot()
plt.title('Age Distribution')
plt.xlabel('Observation Index')
plt.ylabel('Age')
plt.show()
```

---

### Pie Chart — Heart Disease Presence

```python
heart['target'].value_counts().plot(kind='pie', autopct='%1.1f%%', startangle=140)
plt.title('Heart Disease Presence')
plt.axis('equal')
plt.show()
```

---

### Scatter Plot — Age vs Cholesterol (by Heart Disease Presence)

```python
sns.scatterplot(x='age', y='chol', hue='target', data=heart)
plt.title('Age vs Cholesterol Levels')
plt.xlabel('Age')
plt.ylabel('Cholesterol Level')
plt.show()
```

---

### Box Plot — Cholesterol Levels by Heart Disease Presence

```python
sns.boxplot(x='target', y='chol', data=heart)
plt.title('Cholesterol Levels by Heart Disease Presence')
plt.xlabel('Heart Disease Presence (0 = No, 1 = Yes)')
plt.ylabel('Cholesterol Level')
plt.show()
```

---

### Histogram — Age Distribution

```python
sns.histplot(heart['age'], bins=20, kde=True)
plt.title('Age Distribution')
plt.xlabel('Age')
plt.ylabel('Frequency')
plt.show()
```

---

### Heatmap — Correlation Between Numeric Variables

```python
numeric_cols_heart = heart.select_dtypes(include=np.number).columns
corr_heart = heart[numeric_cols_heart].corr()
sns.heatmap(corr_heart, annot=True, cmap='coolwarm')
plt.title('Correlation Heatmap - Heart Disease Data')
plt.show()
```

---

## Part 3: Facebook Data

```python
fb = pd.read_csv('facebook.csv')
```

### Line Plot — Number of Likes Over Time

```python
fb['like'].plot()
plt.title('Number of Likes Over Time')
plt.xlabel('Observation Index')
plt.ylabel('Number of Likes')
plt.show()
```

---

### Pie Chart — Distribution of Post Types

```python
fb['Type'].value_counts().plot(kind='pie', autopct='%1.1f%%', startangle=140)
plt.title('Distribution of Post Types')
plt.axis('equal')
plt.show()
```

---

### Scatter Plot — Likes vs Shares (by Post Type)

```python
sns.scatterplot(x='like', y='share', hue='Type', data=fb)
plt.title('Likes vs Shares by Post Type')
plt.xlabel('Number of Likes')
plt.ylabel('Number of Shares')
plt.show()
```

---

### Box Plot — Likes by Post Type

```python
sns.boxplot(x='Type', y='like', data=fb)
plt.title('Likes by Post Type')
plt.xlabel('Post Type')
plt.ylabel('Number of Likes')
plt.show()
```

---

### Histogram — Distribution of Shares

```python
sns.histplot(fb['share'], bins=20, kde=True)
plt.title('Distribution of Shares')
plt.xlabel('Number of Shares')
plt.ylabel('Frequency')
plt.show()
```

---

### Heatmap — Correlation Between Numeric Variables

```python
numeric_cols_fb = fb.select_dtypes(include=np.number).columns
corr_fb = fb[numeric_cols_fb].corr()
sns.heatmap(corr_fb, annot=True, cmap='coolwarm')
plt.title('Correlation Heatmap - Facebook Data')
plt.show()
```