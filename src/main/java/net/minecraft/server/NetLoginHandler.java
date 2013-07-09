package net.minecraft.server;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.crypto.SecretKey;
import net.canarymod.hook.system.ServerListPingHook;

public class NetLoginHandler extends NetHandler {

    private static Random c = new Random();
    private byte[] d;
    private final MinecraftServer e;
    public final TcpConnection a;
    public boolean b = false;
    private int f = 0;
    private String g = null;
    private volatile boolean h = false;
    private String i = "";
    private boolean j = false;
    private SecretKey k = null;

    public NetLoginHandler(MinecraftServer minecraftserver, Socket socket, String s0) throws IOException {
        this.e = minecraftserver;
        this.a = new TcpConnection(minecraftserver.an(), socket, s0, this, minecraftserver.H().getPrivate());
        this.a.e = 0;
    }

    public void d() {
        if (this.h) {
            this.e();
        }

        if (this.f++ == 600) {
            this.a("Took too long to log in");
        } else {
            this.a.b();
        }
    }

    public void a(String s0) {
        try {
            this.e.an().a("Disconnecting " + this.f() + ": " + s0);
            this.a.a((Packet) (new Packet255KickDisconnect(s0)));
            this.a.d();
            this.b = true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void a(Packet2ClientProtocol packet2clientprotocol) {
        this.g = packet2clientprotocol.f();
        if (!this.g.equals(StringUtils.a(this.g))) {
            this.a("Invalid username!");
        } else {
            PublicKey publickey = this.e.H().getPublic();

            if (packet2clientprotocol.d() != 74) {
                if (packet2clientprotocol.d() > 74) {
                    this.a("Outdated server!");
                } else {
                    this.a("Outdated client!");
                }
            } else {
                this.i = this.e.W() ? Long.toString(c.nextLong(), 16) : "-";
                this.d = new byte[4];
                c.nextBytes(this.d);
                this.a.a((Packet) (new Packet253ServerAuthData(this.i, publickey, this.d)));
            }
        }
    }

    public void a(Packet252SharedKey packet252sharedkey) {
        PrivateKey privatekey = this.e.H().getPrivate();

        this.k = packet252sharedkey.a(privatekey);
        if (!Arrays.equals(this.d, packet252sharedkey.b(privatekey))) {
            this.a("Invalid client reply");
        }

        this.a.a((Packet) (new Packet252SharedKey()));
    }

    public void a(Packet205ClientCommand packet205clientcommand) {
        if (packet205clientcommand.a == 0) {
            if (this.j) {
                this.a("Duplicate login");
                return;
            }

            this.j = true;
            if (this.e.W()) {
                (new ThreadLoginVerifier(this)).start();
            } else {
                this.h = true;
            }
        }
    }

    public void a(Packet1Login packet1login) {}

    public void e() {
        String s0 = this.e.af().a(this.a.c(), this.g);

        if (s0 != null) {
            this.a(s0);
        } else {
            EntityPlayerMP entityplayermp = this.e.af().a(this.g);

            if (entityplayermp != null) {
                this.e.af().a((INetworkManager) this.a, entityplayermp);
            }
        }

        this.b = true;
    }

    public void a(String s0, Object[] aobject) {
        this.e.an().a(this.f() + " lost connection");
        this.b = true;
    }

    public void a(Packet254ServerPing packet254serverping) {
        try {
            ServerConfigurationManager serverconfigurationmanager = this.e.af();
            String s0 = null;

            if (packet254serverping.d()) {
                s0 = this.e.ac() + "\u00a7" + serverconfigurationmanager.k() + "\u00a7" + serverconfigurationmanager.l();
            } else {
                // CanaryMod: ServerListPingHook
                ServerListPingHook hook = (ServerListPingHook) new ServerListPingHook(this.e.ac(), Integer.valueOf(serverconfigurationmanager.k()), Integer.valueOf(serverconfigurationmanager.l())).call();
                if (hook.isCanceled()) {
                    return;
                }
                List list = Arrays.asList(new Serializable[]{ Integer.valueOf(1), Integer.valueOf(74), this.e.z(), hook.getMotd(), hook.getCurrentPlayers(), hook.getMaxPlayers() });
                //
                Object object;

                for (Iterator iterator = list.iterator(); iterator.hasNext(); s0 = s0 + object.toString().replaceAll("\u0000", "")) {
                    object = iterator.next();
                    if (s0 == null) {
                        s0 = "\u00a7";
                    } else {
                        s0 = s0 + "\u0000";
                    }
                }
            }

            InetAddress inetaddress = null;

            if (this.a.g() != null) {
                inetaddress = this.a.g().getInetAddress();
            }

            this.a.a((Packet) (new Packet255KickDisconnect(s0)));
            this.a.d();
            if (inetaddress != null && this.e.ag() instanceof DedicatedServerListenThread) {
                ((DedicatedServerListenThread) this.e.ag()).a(inetaddress);
            }

            this.b = true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void a(Packet packet) {
        this.a("Protocol error");
    }

    public String f() {
        return this.g != null ? this.g + " [" + this.a.c().toString() + "]" : this.a.c().toString();
    }

    public boolean a() {
        return true;
    }

    public boolean c() {
        return this.b;
    }

    static String a(NetLoginHandler netloginhandler) {
        return netloginhandler.i;
    }

    static MinecraftServer b(NetLoginHandler netloginhandler) {
        return netloginhandler.e;
    }

    static SecretKey c(NetLoginHandler netloginhandler) {
        return netloginhandler.k;
    }

    static String d(NetLoginHandler netloginhandler) {
        return netloginhandler.g;
    }

    static boolean a(NetLoginHandler netloginhandler, boolean flag0) {
        return netloginhandler.h = flag0;
    }
}
