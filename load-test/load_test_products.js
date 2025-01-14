import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate } from 'k6/metrics';

export const options = {
    vus: 5,  // 5 virtual users
    duration: '1m',  // Run for 1 minute
};

const BASE_URL = 'http://localhost:3000'; // Change this to your API's base URL
const PAGE_SIZE = 20;

export default function () {
    const url = `${BASE_URL}/api/v1/products?page=0&size=${PAGE_SIZE}`;

    const params = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJIYWNhIiwiaWF0IjoxNzM1OTk3NjYzLCJleHAiOjE3MzYyNTY4NjN9.hahXK3dsctERjsWI8vXqlKNIlv-64h6vAyNgYw9CsbuTgUTwhi8GBk4BzfF_a_suNIODcUKdJiQhiaQj19n4iw',
            // Add any required authentication headers here
        },
    };

    const response = http.get(url, params);

    // Check the response
    check(response, {
        'status is 200': (r) => r.status === 200,
        'rate limit not exceeded': (r) => r.status !== 429, // 429 is typically used for rate limit exceeded
    });

    // Log the response status and time
    console.log(`Status: ${response.status}, Response time: ${response.timings.duration} ms`);

    // Short pause between requests
    sleep(0.1);
}