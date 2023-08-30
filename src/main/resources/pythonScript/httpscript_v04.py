import requests
import time
import concurrent.futures

BASE_URL = "http://localhost:8080/posts/"
BASE_URL_ALL = "http://localhost:8080/posts"

def send_request(method, url):
    try:
        response = requests.request(method, url)

    except Exception as e:
        print(f"Error sending request to {url}: {e}")

def main_post():
    start_time = time.time()
    total_requests = 100
    httpRequests = [
            ("POST", BASE_URL),
            ("DELETE", BASE_URL),
            ("PUT", BASE_URL),
            ("GET", BASE_URL),
            ("GET", BASE_URL),
        ]
    
    with concurrent.futures.ThreadPoolExecutor(max_workers=100) as executor:
        futures = []
        for i in range(1, total_requests + 1):
            randNum = i % 5
            method, url = httpRequests[randNum]
            full_url = f"{url}{i}"
            futures.append(executor.submit(send_request, "POST", full_url))
        
        # Wait for all futures (requests) to complete
        concurrent.futures.wait(futures)
        end_time = time.time()
        print(end_time - start_time)

def main_delete():
    start_time = time.time()
    total_requests = 100
    httpRequests = [
            ("POST", BASE_URL),
            ("DELETE", BASE_URL),
            ("PUT", BASE_URL),
            ("GET", BASE_URL),
            ("GET", BASE_URL),
        ]
    
    with concurrent.futures.ThreadPoolExecutor(max_workers=100) as executor:
        futures = []
        for i in range(1, total_requests + 1):
            randNum = i % 5
            method, url = httpRequests[randNum]
            full_url = f"{url}{i}"
            futures.append(executor.submit(send_request, "DELETE", full_url))
        
        # Wait for all futures (requests) to complete
        concurrent.futures.wait(futures)
        end_time = time.time()
        print(end_time - start_time)


def main_put():
    start_time = time.time()
    total_requests = 100
    httpRequests = [
            ("POST", BASE_URL),
            ("DELETE", BASE_URL),
            ("PUT", BASE_URL),
            ("GET", BASE_URL),
            ("GET", BASE_URL),
        ]
    
    with concurrent.futures.ThreadPoolExecutor(max_workers=100) as executor:
        futures = []
        for i in range(1, total_requests + 1):
            randNum = i % 5
            method, url = httpRequests[randNum]
            full_url = f"{url}{i}"
            futures.append(executor.submit(send_request, "PUT", full_url))
        
        # Wait for all futures (requests) to complete
        concurrent.futures.wait(futures)
        end_time = time.time()
        print(end_time - start_time)


def main_get_by_id():
    start_time = time.time()
    total_requests = 100
    httpRequests = [
            ("POST", BASE_URL),
            ("DELETE", BASE_URL),
            ("PUT", BASE_URL),
            ("GET", BASE_URL),
            ("GET", BASE_URL),
        ]
    
    with concurrent.futures.ThreadPoolExecutor(max_workers=100) as executor:
        futures = []
        for i in range(1, total_requests + 1):
            randNum = i % 5
            method, url = httpRequests[randNum]
            full_url = f"{url}{i}"
            futures.append(executor.submit(send_request, "GET", full_url))
        
        # Wait for all futures (requests) to complete
        concurrent.futures.wait(futures)
        end_time = time.time()
        print(end_time - start_time)
    

def main_get_all():
    start_time = time.time()
    total_requests = 100
    httpRequests = [
            ("POST", BASE_URL),
            ("DELETE", BASE_URL),
            ("PUT", BASE_URL),
            ("GET", BASE_URL),
            ("GET", BASE_URL),
        ]
    
    with concurrent.futures.ThreadPoolExecutor(max_workers=100) as executor:
        futures = []
        for i in range(1, total_requests + 1):
            randNum = i % 5
            method, url = httpRequests[randNum]
            full_url = f"{url}{i}"
            futures.append(executor.submit(send_request, "GET", BASE_URL_ALL))
        
        # Wait for all futures (requests) to complete
        concurrent.futures.wait(futures)
        end_time = time.time()
        print(end_time - start_time)



if __name__ == "__main__":
    main_post()
    main_delete()
    main_put()
    main_get_by_id()
    main_get_all()
    print(f"Application finished running")
