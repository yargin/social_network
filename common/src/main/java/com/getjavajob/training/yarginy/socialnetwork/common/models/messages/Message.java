package com.getjavajob.training.yarginy.socialnetwork.common.models.messages;

import com.getjavajob.training.yarginy.socialnetwork.common.models.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.Model;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Arrays.copyOf;

@Component
@Scope("prototype")
@MappedSuperclass
public abstract class Message<E extends Model> implements Model {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "message")
    protected String text;
    protected byte[] image;
    @Column(name = "posted")
    protected Timestamp posted;
    @Version
    private long version;

    public abstract void setReceiver(E receiver);

    public abstract E getReceiver();

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = copyOf(image, image.length);
    }

    public Timestamp getPosted() {
        return posted;
    }

    public void setPosted(Timestamp date) {
        this.posted = date;
    }

    public abstract Account getAuthor();

    public abstract void setAuthor(Account author);

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("not supported");
    }

    protected boolean implementedEquals(Message<? extends Model> message) {
        return Objects.equals(text, message.text) && Arrays.equals(image, message.image) &&
                Objects.equals(posted, message.posted) && Objects.equals(getReceiver(), message.getReceiver()) &&
                Objects.equals(getAuthor(), message.getAuthor());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(text, posted, getReceiver(), getAuthor());
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
