package com.getjavajob.training.yarginy.socialnetwork.dao.factories;

import com.getjavajob.training.yarginy.socialnetwork.common.models.account.Account;
import com.getjavajob.training.yarginy.socialnetwork.common.models.group.Group;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.SelfManyToManyDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.selfrelated.dmls.FriendshipDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDao;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.ManyToManyDaoImpl;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.dmls.FriendshipsRequestsDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.dmls.GroupsMembersDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.dmls.GroupsMembershipsRequestsDml;
import com.getjavajob.training.yarginy.socialnetwork.dao.relationsdao.manytomany.variousrelated.dmls.GroupsModeratorsDml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DbFactory {
    private DataSource dataSource;
    private NamedParameterJdbcTemplate template;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        template = new NamedParameterJdbcTemplate(dataSource);
    }

//    @Bean("accountDao")
//    @Autowired
//    public Dao<Account> getAccountDao(AccountDaoHandler handler) {
//        return new DaoImpl<>(dataSource, handler);
//    }

//    @Bean("dialogDao")
//    @Autowired
//    public Dao<Dialog> getDialogDao(DialogDaoHandler handler) {
//        return new DaoImpl<>(dataSource, handler);
//    }

//    @Bean("groupDao")
//    @Autowired
//    public BatchDao<Group> getGroupDao(BatchGroupDml dml) {
//        return new BatchDaoImpl<>(dataSource, dml);
//    }

    @Bean("groupMembershipDao")
    @Autowired
    public ManyToManyDao<Account, Group> getGroupMembershipDao(GroupsMembersDml dml) {
        return new ManyToManyDaoImpl<>(dataSource, dml);
    }

    @Bean("friendshipDao")
    @Autowired
    public SelfManyToManyDao<Account> getFriendshipDao(FriendshipDml dml) {
        return new SelfManyToManyDaoImpl<>(dataSource, dml);
    }

//    @Bean("phonesDao")
//    @Autowired
//    public BatchDao<Phone> getPhoneDao(BatchPhonesDml dml) {
//        return new BatchDaoImpl<>(dataSource, dml);
//    }

//    @Bean("accountPhonesDao")
//    @Autowired
//    public OneToManyDao<Phone> getAccountsPhones(AccountPhonesDml dml) {
//        return new OneToManyDaoImpl<>(dataSource, dml);
//    }

//    @Bean("passwordDao")
//    @Autowired
//    public Dao<Password> getPasswordDao(PasswordDml dml) {
//        return new PasswordDao(dataSource, dml);
//    }

//    @Bean("accountOwnerGroupsDao")
//    @Autowired
//    public OneToManyDao<Group> getAccountsOwnedGroupsDao(AccountGroupsDml dml) {
//        return new OneToManyDaoImpl<>(dataSource, dml);
//    }

    @Bean("groupModeratorsDao")
    @Autowired
    public ManyToManyDao<Account, Group> getGroupModeratorsDao(GroupsModeratorsDml dml) {
        return new ManyToManyDaoImpl<>(dataSource, dml);
    }

    @Bean("groupRequestsDao")
    @Autowired
    public ManyToManyDao<Account, Group> getGroupRequestsDao(GroupsMembershipsRequestsDml dml) {
        return new ManyToManyDaoImpl<>(dataSource, dml);
    }

    @Bean("friendshipRequestsDao")
    @Autowired
    public ManyToManyDao<Account, Account> getFriendshipRequestsDao(FriendshipsRequestsDml dml) {
        return new ManyToManyDaoImpl<>(dataSource, dml);
    }

//    @Bean("accountWallMessageDao")
//    @Autowired
//    public Dao<Message> getAccountWallMessageDao(AccountWallMessageDml dml) {
//        return new DaoImplOld<>(dataSource, dml);
//    }

//    @Bean("accountWallMessagesDao")
//    public OneToManyDao<Message> getAccountWallMessagesDao(AccountWallMessagesDml dml) {
//        return new OneToManyDaoImpl<>(dataSource, dml);
//    }
//
//    @Bean("dialogMessageDao")
//    public Dao<Message> getDialogMessageDao(DialogMessageDml dml) {
//        return new DaoImplOld<>(dataSource, dml);
//    }
//
//    @Bean("dialogMessagesDao")
//    public OneToManyDao<Message> getDialogsMessagesDao(DialogsMessagesDml dml) {
//        return new OneToManyDaoImpl<>(dataSource, dml);
//    }
//
//    @Bean("groupWallMessageDao")
//    public Dao<Message> getGroupWallMessageDao(GroupWallMessageDml dml) {
//        return new DaoImplOld<>(dataSource, dml);
//    }
//
//    @Bean("groupWallMessagesDao")
//    public OneToManyDao<Message> getGroupWallMessagesDao(GroupWallMessagesDml dml) {
//        return new OneToManyDaoImpl<>(dataSource, dml);
//    }

//    @Bean("dialogsDao")
//    public OneToManyDao<Dialog> getAccountDialogsDao(AccountDialogsDml dml) {
//        return new OneToManyDaoImpl<>(dataSource, dml);
//    }
}
