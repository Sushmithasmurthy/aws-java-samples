package com.ss.model;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ShellConnectionStream {

   /* JSch ssh;
    String usuario;
    String senha;
    String host;
    int porta;

    public ShellConnectionStream(String usuario, String senha, String host, int porta) {

        try {
            ssh = new JSch();
            this.usuario = usuario;
            this.senha = senha;
            this.porta = porta;
            this.host = host;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean connect() throws JSchException {
        Session session;
        try {

             session = ssh.getSession(this.usuario, this.host, this.porta);
            session.setPassword(this.senha);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(30000);
            setSession(session);
            setReady(true);

            return true;
        } catch (Exception e) {
            setReady(false);
        }
        return false;
    }

    public void close() {

        if (channel != null)
            channel.disconnect();

        if (session != null)
            session.disconnect();

        setReady(false);
    }

    public String write(String comando) {

        try {

            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(comando);
            setCommandOutput(channel.getInputStream());
            channel.connect(30000);

            StringBuilder sBuilder = new StringBuilder();
            String lido = reader.readLine();

            while (lido != null) {
                sBuilder.append(lido);
                sBuilder.append("\n");
                lido = reader.readLine();
            }

            return sBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean upload(String origem,String dirDestino) {

        try {
            File origem_ = new File(origem);
            dirDestino = dirDestino.replace(" ", "_");
            String destino = dirDestino.concat("/").concat(origem_.getName());
            return upload(origem, destino, dirDestino);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean upload(String origem,String destino,String dirDestino) {

        try {
            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
            dirDestino = dirDestino.replace(" ", "_");
            sftp.cd(dirDestino);
            sftp.put(origem, destino);
            sftp.disconnect();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }*/
}
