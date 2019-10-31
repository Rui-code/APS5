import sqlite3  # módulo que lida com conexão ao sqlite
import _thread  # módulo que lida com threads
import poplib  # módulo que lida com recebimento de emails
import smtplib  # módulo que lida com envio de emails
import email.utils  # módulo utilitário para emails
from socket import socket, AF_INET, SOCK_STREAM  # módulo que lida com conexão via rede
from threading import Thread  # módulo que lida com threads


class User:  # classe de usuários para acesso ao banco
    def __init__(self):
        self.name = None
        self.passwd = None


class DAO:  # classe que acessa o banco de dados
    def __init__(self):
        self.con = sqlite3.connect('usuários.db')  # cria a conexão com o db
        self.cur = self.con.cursor()  # cursor que executará as querys
        self.sql = "SELECT nome, senha FROM usuarios"  # query

    def access(self, user):
        try:
            self.cur.execute(self.sql)  # execução da query
            self.data = [data for data in self.cur.fetchall()]  # atribui os dados ao vetor data

            if self.data[0] == user.name and self.data[1] == user.passwd:  # compara os valores
                print('login aprovado')
                return True
            else:
                return False
        except ConnectionError as msg:
            return False
        finally:
            self.con.close()  # fecha a conexão


class DTO:  # classe que transfere para o banco de dados
    def __init__(self):
        self.con = sqlite3.connect('usuários.db')  # cria a conexão com o db
        self.cur = self.con.cursor()  # cursor que executará as querys
        self.sql = "INSERT INTO usuarios (nome, senha) VALUES (?, ?)"  # query

    def access(self, user):
        try:
            self.cur.execute(self.sql, (user.name, user.passwd))  # execução da query
            self.con.commit()  # confirmação das mudanças
            return True
        except ConnectionError as msg:
            return False
        finally:
            self.con.close()  # fecha a conexão


class ChatServer:  # classe servidor do chat
    def __init__(self):
        self.my_host = ''  # cria o nome do host
        self.my_port = 8080  # define uma porta para usar
        self.sock = socket(AF_INET, SOCK_STREAM)  # cria o socket
        self.sock.bind((self.my_host, self.my_port))  # coloca o servidor no ar
        self.sock.listen(5)  # escuta na porta 8080 esperando por conexões
        self.dispatch()  # chama o método que atribui os clientes as threads

    def handle_client(self):  # classe que trata o cliente
        while True:
            self.data = self.conex.recv(1024)  # recebe os dados do cliente

            if not self.data:  # se vazio para-se o loop
                break

            self.response = self.end, 'disse: %s' % self.data  # escreve a mensagem
            self.conex.send(self.response.encode())  # faz o broadcast da mensagem

        self.conex.close()  # fecha a conexão

    def dispatch(self):
        while True:
            self.conex, self.end = self.sock.accept()  # aceita uma conexão
            print(self.end, 'entrou..')  # por na view
            _thread.start_new_thread(self.handle_client, (self.conex,))  # manda o cliente para a thread


class ChatClient(Thread):  # classe cliente do chat
    def __init__(self, id, server, port, *mensagem):
        self.id = 'localhost'  # id do cliente
        self.server = server  # servidor a ser conectado
        self.port = 8080  # port que será usada
        self.msgs = mensagem  # mensagem que será enviada
        self.sock = socket(AF_INET, SOCK_STREAM)  # socket que será usado
        Thread.__init__(self)  # thread q rodará os clientes

    def run(self):  # executa o cliente
        self.sock.connect(self.server)  # conecta no server

        for line in self.msgs:  # manda a mgs linha por linha
            self.sock.send(line)

            self.data = self.sock.recv(1024)  # espera uma resposta do servidor
            print('Cliente', self.id, 'recebeu:', self.data)  # imprime a mensagem

        self.sock.close()  # fecha o socket


class MailReceiver:  # classe que recebe emails utilizando o POP3
    def __init__(self):
        self.mail_server = 'Outlook.office365.com'  # nome do servidor pop
        self.mail_user = ''  # nome do usuário - pegar da view
        self.mail_passwd = ''  # senha do usuários - pegar da view
        self.server = poplib.POP3_SSL(self.mail_server)  # define o server

    def connect(self):  # método que conecta ao servidor
        self.server.user(self.mail_user)  # atribui o usuário no servidor
        self.server.pass_(self.mail_passwd)  # atribui a senha no servidor

        try:
            print(self.server.getwelcome())  # pega uma mensagem de boas vindas - maybe exibir na view

            msg_count = self.server.stat()  # obtem a qtde de mensagens - maybe exibir na view

            for msgs in range(msg_count):  # percorre as mensagens da caixa de mensagens
                header, messages = self.server.retr(msgs + 1)

                for line in messages:
                    print(line.decode())  # le a mensagem linha por linha

                if msgs < msg_count - 1:  # checa se exitem mais mensagens
                    print('Sem mais mensagens')  # por na view
        finally:
            self.server.quit()  # sai do servidor


class MailSender:  # classe que manda o email
    def __init__(self):
        self.mail_server = 'SMTP.office365.com'
        self.mail_port = '587'
        self.mail_user = ''  # pegar da view
        self.mail_passwd = ''  # pegar da view
        self.mail_from = ''.strip()  # peger da view
        self.mail_to = ''.strip()  # pegar da view
        self.mail_tos = self.mail_to.split(';')
        self.subject = ''.strip()  # pegar da view
        self.date = email.utils.formatdate()  # estabelece uma data para o email
        self.msg = 'From: %s\nTo: %s\nDate: %s\nSubject: %s\n\n' % (self.mail_from, self.mail_to, self.date,
                                                                    self.subject)  # pegar o resto da view
        self.server = smtplib.SMTP(self.mail_server, self.mail_port)  # define o server

    def send(self):  # método que manda o email
        self.server.starttls()  # criptografia usada
        self.server.login(self.mail_user, self.mail_passwd)  # loga no servidor
        fail = self.server.sendmail(self.mail_from, self.mail_tos, self.msg)  # manda o email
        self.server.quit()  # sai do servidor
