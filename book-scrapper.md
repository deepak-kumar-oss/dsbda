# Web Scraping — Books to Scrape

> **Note:** Amazon blocks scrapers, so this assignment uses an open practice site instead.
>
> **Target site:** [books.toscrape.com](http://books.toscrape.com/catalogue/page-1.html) — a mock e-commerce site with books, prices, and ratings. It has **50 pages** with **20 books each** (1,000 books total).

---

## Libraries

```python
import requests
import pandas as pd
from bs4 import BeautifulSoup
```

---

## Configuration

```python
BASE_URL  = 'http://books.toscrape.com/catalogue/page-{}.html'
NUM_PAGES = 5  # site has 50 pages total; adjust as needed

# Map word-based ratings to numbers
RATING_MAP = {'One': 1, 'Two': 2, 'Three': 3, 'Four': 4, 'Five': 5}
```

---

## Scraping Loop

```python
books = []

for page in range(1, NUM_PAGES + 1):
    response = requests.get(BASE_URL.format(page))
    soup     = BeautifulSoup(response.text, 'lxml')

    for book in soup.select('article.product_pod'):
        title        = book.select_one('h3 a')['title']
        price        = book.select_one('.price_color').text.strip()
        rating       = RATING_MAP.get(book.select_one('p.star-rating')['class'][1], 0)
        availability = book.select_one('.availability').text.strip()

        books.append({
            'Title'       : title,
            'Price'       : price,
            'Rating'      : rating,
            'Availability': availability
        })

    print(f'Page {page} done')
```

---

## Saving Results

```python
df = pd.DataFrame(books)
df.to_csv('books.csv', index=False)

print(f'Done! {len(df)} books saved.')
```

---

## How It Works

| Step | What happens |
|---|---|
| `requests.get()` | Fetches the raw HTML of each page |
| `BeautifulSoup` | Parses the HTML into a searchable tree |
| `soup.select()` | Finds all `<article>` elements (one per book) |
| `RATING_MAP` | Converts word ratings (`'Three'`) to integers (`3`) |
| `pd.DataFrame` | Collects all books into a structured table |
| `to_csv()` | Exports the result to `books.csv` |

---

## Sample Output

| Title | Price | Rating | Availability |
|---|---|---|---|
| A Light in the Attic | £51.77 | 3 | In stock |
| Tipping the Velvet | £53.74 | 1 | In stock |
| Soumission | £50.10 | 1 | In stock |

> To scrape all 1,000 books, set `NUM_PAGES = 50`.