import time
from locust import HttpUser, task, between

class QuickstartUser(HttpUser):
    wait_time = between(0.2, 0.8)
    user_id=""

    @task
    def move_up(self):
        user_id=self.user_id
        self.client.post(f"/move/{user_id}?direction=up",name="/move")
    
    @task()
    def move_down(self):
        user_id=self.user_id
        self.client.post(f"/move/{user_id}?direction=down",name="/move")
    @task()
    def move_left(self):
        user_id=self.user_id
        self.client.post(f"/move/{user_id}?direction=left",name="/move")
    @task()
    def move_right(self):
        user_id=self.user_id
        self.client.post(f"/move/{user_id}?direction=right",name="/move")

    def on_start(self):
        response=self.client.post("/players")
        self.user_id=response.text
