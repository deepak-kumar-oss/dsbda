# Snapdeal Review Scraper

A Python web scraper that extracts product reviews from Snapdeal and saves them to a CSV file.

---

## Complete Code

```python
import requests
from bs4 import BeautifulSoup
import pandas as pd
import time

def scrape_reviews(product_url, pages=10):
    headers = {'user-agent': 'Mozilla/5.0'}
    all_reviews = []

    for page in range(1, pages + 1):
        soup = BeautifulSoup(
            requests.get(f"{product_url}?page={page}", headers=headers).text,
            "html.parser"
        )
        blocks = soup.find_all("div", class_="commentlist")
        if not blocks:
            break

        for block in blocks:
            user_review = block.find("div", class_="user-review")
            if not user_review:
                continue

            parts = user_review.get_text("|", strip=True).split("|")
            byline = (block.find("div", class_="_reviewUserName") or BeautifulSoup("", "html.parser")).get_text(strip=True)

            all_reviews.append({
                "username": (block.find("div", class_="hidden") or BeautifulSoup("", "html.parser")).get_text(strip=True),
                "date":     byline.split(" on ")[-1] if " on " in byline else "",
                "rating":   len(user_review.find_all("i", class_="sd-icon-star active")),
                "title":    parts[0],
                "comment":  next(("|".join(parts[i+1:]) for i, p in enumerate(parts) if "Verified Buyer" in p), ""),
            })

        print(f"Page {page}: {len(blocks)} reviews scraped")
        time.sleep(1)

    return pd.DataFrame(all_reviews).drop_duplicates()


df = scrape_reviews("https://www.snapdeal.com/product/nofilter-light-blue-cotton-mens/620060215901/reviews")
print(df)
df.to_csv("snapdeal_reviews.csv", index=False)
print(f"Saved {len(df)} reviews.")
```


