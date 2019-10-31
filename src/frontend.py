import backend  # modulo que contem as lógica da aplicação
from tkinter import *


class Login:  # classe que controla o login
    def __init__(self):
        self.window = Tk()

        def btn_login_click():
            if self.txt_user.get() == '' or self.txt_passwd.get() == '':
                lb_warning = Label(self.window, text="!!!Todos os campos são obrigatórios!!!")
                lb_warning.place(x=50, y=150)
            else:
                self.login(self.txt_user.get(), self.txt_passwd.get())

        def quitt():
            self.window.destroy()

        self.lb_user = Label(self.window, text='Usuário: ')
        self.lb_user.place(x=25, y=50)
        self.lb_passwd = Label(self.window, text='Senha: ')
        self.lb_passwd.place(x=25, y=100)

        self.txt_user = Entry(self.window, width=60)
        self.txt_user.place(x=80, y=50)
        self.txt_passwd = Entry(self.window, width=60)
        self.txt_passwd.place(x=80, y=100)

        self.btn_login = Button(self.window, width=15, text='Logar', command=btn_login_click)
        self.btn_login.place(x=25, y=200)
        self.btn_quit = Button(self.window, width=15, text='Sair', command=quitt)
        self.btn_quit.place(x=425, y=200)
        self.btn_signup = Button(self.window, width=15, text='Cadastrar-se', command=SignUp)
        self.btn_signup.place(x=225, y=200)

        self.window.geometry("600x250+400+100")
        self.window.mainloop()

    # coloca os dados do login no user e manda para o DAO
    def login(self, username, passwd):
        user = backend.User()
        user.name = username
        user.passwd = passwd
        logged = backend.DAO().access(user)  # passa o objeto de usuário para o DAO

        if logged:
            self.window.destroy()
            Welcome(user.name)
        else:
            lb_warning = Label(self.window, text="!!!Erro ao Logar!!!")
            lb_warning.place(x=50, y=150)


class SignUp:  # classe que controla o cadastro
    def __init__(self):
        self.window = Tk()

        def btn_signup_click():
            lb_warning = Label(self.window)
            lb_warning.place(x=50, y=180)
            if self.txt_user.get() == '' or self.txt_passwd.get() == '' or self.txt_passwd_rp.get() == '':
                lb_warning["text"] = "!!!Todos os campos são obrigatórios!!!"
            elif self.txt_passwd.get() != self.txt_passwd_rp.get():
                lb_warning["text"] = "!!!!!As senhas precisam ser iguais!!!!!"
            else:
                self.create_sign_up(self.txt_user.get(), self.txt_passwd.get(), self.txt_passwd_rp.get())

        def quitt():
            self.window.destroy()
            Login()

        self.lb_user = Label(self.window, text='Usuário: ')
        self.lb_user.place(x=25, y=50)
        self.lb_passwd = Label(self.window, text='Senha: ')
        self.lb_passwd.place(x=25, y=100)
        self.lb_passwd_rp = Label(self.window, text='Repita a Senha: ')
        self.lb_passwd_rp.place(x=25, y=150)

        self.txt_user = Entry(self.window, width=60)
        self.txt_user.place(x=80, y=50)
        self.txt_passwd = Entry(self.window, width=60)
        self.txt_passwd.place(x=80, y=100)
        self.txt_passwd_rp = Entry(self.window, width=54)
        self.txt_passwd_rp.place(x=125, y=150)

        self.btn_send = Button(self.window, width=15, text='Enviar', command=btn_signup_click)
        self.btn_send.place(x=100, y=200)
        self.btn_back = Button(self.window, width=15, text='Voltar', command=quitt)
        self.btn_back.place(x=350, y=200)

        self.window.geometry("600x250+400+100")
        self.window.mainloop()

    # coloca os dados do cadastro no user e manda para o dao
    def create_sign_up(self, username, passwd, test_passwd):

        user = backend.User()
        user.name = username
        user.passwd = passwd
        signed_up = backend.DTO().access(user)  # passa o objeto de usuário para o DAO

        if signed_up:
            self.window.destroy()
            Login()
        else:
            lb_warning = Label(self.window, text="!!!Erro ao se Cadastrar!!!")
            lb_warning.place(x=50, y=180)


class Welcome:
    def __init__(self, name='Rui'):
        self.window = Tk()

        def quitt():
            self.window.destroy()
            Login()

        def mail():
            self.window.destroy()
            LoginMail()

        def chat():
            self.window.destroy()
            Chat()

        self.lb_welcome = Label(self.window, text='Olá %s, o que deseja fazer?' % name)
        self.lb_welcome.place(x=200, y=50)

        self.btn_chat = Button(self.window, width=15, text='Ir para o chat', command=chat)
        self.btn_chat.place(x=25, y=100)
        self.btn_quit = Button(self.window, width=15, text='Sair', command=quitt)
        self.btn_quit.place(x=425, y=100)
        self.btn_login_mail = Button(self.window, width=15, text='Acessar o e-mail', command=mail)
        self.btn_login_mail.place(x=225, y=100)

        self.window.geometry("600x150+400+100")
        self.window.mainloop()


class Chat:
    pass


class LoginMail:
    pass


if __name__ == '__main__':
    # Login()
    Welcome()
