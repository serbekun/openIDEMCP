import requests
import base64
import json

URL = "http://localhost:8080"

def generate_task_code(prompt_text, task_id, model, explanation=True) -> str:

    payload = {
        "prompt": prompt_text,
        "task_id": task_id,
        "model": model,
        "explanation_mode": explanation
    }
    
    response = requests.post(
        f"{URL}/v0/api/generate_task_code",
        headers={'Content-Type': 'application/json'},
        json=payload
    )
    
    return response.json()

def query_model():
    payload = {
        "prompt": "how to init variable on C",
        "model": "gemma3:1b",
    }

    response = requests.post(
        f"{URL}/v0/api/query_model",
        headers={'Content-Type': 'application/json'},
        json=payload
    )

    return response.json()

if __name__ == "__main__":

    print("doing query model ========================")
    result1 = query_model()
    print(json.dumps(result1, indent=4))

    print("doing generate task code =================")
    result = generate_task_code(
        prompt_text="Create a function to check prime numbers",
        task_id=1,
        model="gemma3:1b",
        explanation=True
    )
    print(json.dumps(result, indent=4))

    print("code:")
    print(result["code"])

    