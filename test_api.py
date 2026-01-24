import requests
import base64
import json

def generate_task_code(url, prompt_text, task_id, model, explanation=True):

    prompt_base64 = base64.b64encode(prompt_text.encode('utf-8')).decode('utf-8')
    
    payload = {
        "prompt": prompt_base64,
        "task_id": task_id,
        "model": model,
        "explanation_mode": explanation
    }
    
    response = requests.post(
        f"{url}/v0/api/generate_task_code",
        headers={'Content-Type': 'application/json'},
        json=payload
    )
    
    return response.json()

if __name__ == "__main__":
    result = generate_task_code(
        url="http://localhost:8080",
        prompt_text="Create a function to check prime numbers",
        task_id=1,
        model="gemma3:1b",
        explanation=True
    )
    print(json.dumps(result, indent=2))