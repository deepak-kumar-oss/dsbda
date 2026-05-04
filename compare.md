# Python Visualisation — Dataset Comparison

A side-by-side comparison of all plot types across the three datasets: **Air Quality**, **Heart Disease**, and **Facebook**.

---

## 1. Line Plot

| Air Quality | Heart Disease | Facebook |
|---|---|---|
| **Ozone Levels Over Time** | **Age Distribution** | **Number of Likes Over Time** |
| Plots `Ozone` concentration by observation index. Useful for spotting temporal trends or spikes in air pollution. | Plots patient `age` by observation index. Shows the range and spread of ages in the dataset. | Plots `like` count by observation index. Reveals engagement spikes and patterns across posts. |
| `air['Ozone'].plot()` | `heart['age'].plot()` | `fb['like'].plot()` |

```python
# Air Quality
air['Ozone'].plot()
plt.title('Ozone Levels Over Time')
plt.xlabel('Observation Index'); plt.ylabel('Ozone Concentration')

# Heart Disease
heart['age'].plot()
plt.title('Age Distribution')
plt.xlabel('Observation Index'); plt.ylabel('Age')

# Facebook
fb['like'].plot()
plt.title('Number of Likes Over Time')
plt.xlabel('Observation Index'); plt.ylabel('Number of Likes')
```

---

## 2. Pie Chart

| Air Quality | Heart Disease | Facebook |
|---|---|---|
| **Observations by Month** | **Heart Disease Presence** | **Distribution of Post Types** |
| Shows how observations are distributed across months (5–9). Helps identify sampling balance. | Shows the proportion of patients with (1) vs without (0) heart disease. | Breaks down post types (Photo, Video, Link, Status) by share of total posts. |
| `air['Month'].value_counts()` | `heart['target'].value_counts()` | `fb['Type'].value_counts()` |

```python
# Air Quality
air['Month'].value_counts().plot(kind='pie', autopct='%1.1f%%', startangle=140)

# Heart Disease
heart['target'].value_counts().plot(kind='pie', autopct='%1.1f%%', startangle=140)

# Facebook
fb['Type'].value_counts().plot(kind='pie', autopct='%1.1f%%', startangle=140)
```

---

## 3. Scatter Plot

| Air Quality | Heart Disease | Facebook |
|---|---|---|
| **Wind vs Ozone Levels** | **Age vs Cholesterol (by Target)** | **Likes vs Shares (by Post Type)** |
| Examines relationship between wind speed and ozone concentration. Higher wind may disperse ozone. | Explores how age and cholesterol interact; color-coded by heart disease presence. | Shows correlation between likes and shares; color-coded by post type to reveal engagement patterns. |
| X: `Wind`, Y: `Ozone` | X: `age`, Y: `chol`, hue: `target` | X: `like`, Y: `share`, hue: `Type` |

```python
# Air Quality
sns.scatterplot(x='Wind', y='Ozone', data=air)

# Heart Disease
sns.scatterplot(x='age', y='chol', hue='target', data=heart)

# Facebook
sns.scatterplot(x='like', y='share', hue='Type', data=fb)
```

---

## 4. Bar Plot / Box Plot

> Note: Air Quality uses a **Bar Plot** for monthly averages; Heart Disease and Facebook use **Box Plots** for distribution by category.

| Air Quality | Heart Disease | Facebook |
|---|---|---|
| **Avg Ozone Levels by Month** *(Bar)* | **Cholesterol by Heart Disease** *(Box)* | **Likes by Post Type** *(Box)* |
| Shows the mean ozone concentration per month with confidence intervals. Month 7 (July) often peaks. | Compares cholesterol distribution between patients with and without heart disease. | Compares like distributions across post types — reveals which content format drives more engagement. |
| `sns.barplot(x='Month', y='Ozone')` | `sns.boxplot(x='target', y='chol')` | `sns.boxplot(x='Type', y='like')` |

```python
# Air Quality — Bar Plot
sns.barplot(x='Month', y='Ozone', data=air)
plt.title('Average Ozone Levels by Month')

# Heart Disease — Box Plot
sns.boxplot(x='target', y='chol', data=heart)
plt.title('Cholesterol Levels by Heart Disease Presence')

# Facebook — Box Plot
sns.boxplot(x='Type', y='like', data=fb)
plt.title('Likes by Post Type')
```

---

## 5. Box Plot (Air Quality Only) vs Histogram

| Air Quality | Heart Disease | Facebook |
|---|---|---|
| **Ozone Levels by Month** *(Box)* | **Age Distribution** *(Histogram)* | **Distribution of Shares** *(Histogram)* |
| Shows ozone spread and outliers per month. Higher variance in summer months. | Bell-curve-like distribution of patient ages; peaks around 50–60. | Highly right-skewed distribution — most posts get few shares, with rare viral outliers. |
| `sns.boxplot(x='Month', y='Ozone')` | `sns.histplot(heart['age'], bins=20, kde=True)` | `sns.histplot(fb['share'], bins=20, kde=True)` |

```python
# Air Quality — Box Plot
sns.boxplot(x='Month', y='Ozone', data=air)
plt.title('Ozone Levels by Month')

# Heart Disease — Histogram
sns.histplot(heart['age'], bins=20, kde=True)
plt.title('Age Distribution')

# Facebook — Histogram
sns.histplot(fb['share'], bins=20, kde=True)
plt.title('Distribution of Shares')
```

---

## 6. Histogram

| Air Quality | Heart Disease | Facebook |
|---|---|---|
| **Distribution of Ozone Levels** | **Age Distribution** | **Distribution of Shares** |
| Right-skewed; most ozone readings are low with occasional high spikes. KDE overlay shows the shape. | Roughly normal; centered around mid-50s. KDE helps visualise the smooth age distribution. | Heavily right-skewed; viral posts create a long tail. Log scale may be more informative. |
| `sns.histplot(air['Ozone'], bins=20, kde=True)` | `sns.histplot(heart['age'], bins=20, kde=True)` | `sns.histplot(fb['share'], bins=20, kde=True)` |

```python
# Air Quality
sns.histplot(air['Ozone'], bins=20, kde=True)
plt.title('Distribution of Ozone Levels')

# Heart Disease
sns.histplot(heart['age'], bins=20, kde=True)
plt.title('Age Distribution')

# Facebook
sns.histplot(fb['share'], bins=20, kde=True)
plt.title('Distribution of Shares')
```

---

## 7. Heatmap — Correlation Matrix

| Air Quality | Heart Disease | Facebook |
|---|---|---|
| **Correlation Between Numeric Variables** | **Correlation Between Numeric Variables** | **Correlation Between Numeric Variables** |
| Includes: Ozone, Solar.R, Wind, Temp, Month, Day. `Temp` and `Ozone` tend to be positively correlated; `Wind` and `Ozone` negatively correlated. | Includes: age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, target. `cp` (chest pain) and `target` often show notable correlation. | Includes engagement metrics (like, share, comment) and time features. `like` and `share` typically show strong positive correlation. |

```python
# Air Quality
numeric_cols = air.select_dtypes(include=np.number).columns
sns.heatmap(air[numeric_cols].corr(), annot=True, cmap='coolwarm')
plt.title('Correlation Heatmap')

# Heart Disease
numeric_cols_heart = heart.select_dtypes(include=np.number).columns
sns.heatmap(heart[numeric_cols_heart].corr(), annot=True, cmap='coolwarm')
plt.title('Correlation Heatmap - Heart Disease Data')

# Facebook
numeric_cols_fb = fb.select_dtypes(include=np.number).columns
sns.heatmap(fb[numeric_cols_fb].corr(), annot=True, cmap='coolwarm')
plt.title('Correlation Heatmap - Facebook Data')
```

---

## Summary Table

| Plot Type | Air Quality | Heart Disease | Facebook |
|---|---|---|---|
| **Line** | Ozone over time | Age by index | Likes over time |
| **Pie** | Obs. by month | Heart disease presence | Post type distribution |
| **Scatter** | Wind vs Ozone | Age vs Cholesterol (hued) | Likes vs Shares (hued) |
| **Bar** | Avg Ozone by Month | *(not used)* | *(not used)* |
| **Box** | Ozone by Month | Cholesterol by Target | Likes by Post Type |
| **Histogram** | Ozone distribution | Age distribution | Share distribution |
| **Heatmap** | Numeric correlations | Numeric correlations | Numeric correlations |

---

*All plots use `matplotlib.pyplot` and `seaborn`. Data loaded via `pandas.read_csv()`.*