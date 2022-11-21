# Gerimedica

Thank You that you are looking at my code, below I'll try to describe the setup process and how to use this app.
### How to run?

1) get sources 
2) Make sure that ports `8080` and `5432` are free
3) Run `docker-compose` by executing script `./restart.sh`
```bash
cd docker
./restart.sh
```

In case that your env doesn't have `maven` installed, then you can try a command below.
1) run `./restart_from_zip.sh`
I've placed an assembled artifact in the jar folder

### How to interact with?

Visit `http://localhost:8080` You will be automatically redirected to the swagger

### Feel free to contact me in case of any problems with setup!
* [Telegram](https://t.me/AlexanderLuchko)
* Email : **alex.alex.95@mail.ru**

to stop all components run `./stop.sh`