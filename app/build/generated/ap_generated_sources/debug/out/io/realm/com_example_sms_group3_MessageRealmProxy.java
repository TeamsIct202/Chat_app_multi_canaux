package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.ImportFlag;
import io.realm.ProxyUtils;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.NativeContext;
import io.realm.internal.OsList;
import io.realm.internal.OsMap;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.OsSet;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.internal.core.NativeRealmAny;
import io.realm.internal.objectstore.OsObjectBuilder;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class com_example_sms_group3_MessageRealmProxy extends com.example.sms_group3.Message
    implements RealmObjectProxy, com_example_sms_group3_MessageRealmProxyInterface {

    static final class MessageColumnInfo extends ColumnInfo {
        long idColKey;
        long phoneNumberColKey;
        long textColKey;
        long dateColKey;
        long typeColKey;

        MessageColumnInfo(OsSchemaInfo schemaInfo) {
            super(5);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Message");
            this.idColKey = addColumnDetails("id", "id", objectSchemaInfo);
            this.phoneNumberColKey = addColumnDetails("phoneNumber", "phoneNumber", objectSchemaInfo);
            this.textColKey = addColumnDetails("text", "text", objectSchemaInfo);
            this.dateColKey = addColumnDetails("date", "date", objectSchemaInfo);
            this.typeColKey = addColumnDetails("type", "type", objectSchemaInfo);
        }

        MessageColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new MessageColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final MessageColumnInfo src = (MessageColumnInfo) rawSrc;
            final MessageColumnInfo dst = (MessageColumnInfo) rawDst;
            dst.idColKey = src.idColKey;
            dst.phoneNumberColKey = src.phoneNumberColKey;
            dst.textColKey = src.textColKey;
            dst.dateColKey = src.dateColKey;
            dst.typeColKey = src.typeColKey;
        }
    }

    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private MessageColumnInfo columnInfo;
    private ProxyState<com.example.sms_group3.Message> proxyState;

    com_example_sms_group3_MessageRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (MessageColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.sms_group3.Message>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.idColKey);
    }

    @Override
    public void realmSet$id(long value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$phoneNumber() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.phoneNumberColKey);
    }

    @Override
    public void realmSet$phoneNumber(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.phoneNumberColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.phoneNumberColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.phoneNumberColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.phoneNumberColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$text() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.textColKey);
    }

    @Override
    public void realmSet$text(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.textColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.textColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.textColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.textColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$date() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.dateColKey);
    }

    @Override
    public void realmSet$date(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.dateColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.dateColKey, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.typeColKey);
    }

    @Override
    public void realmSet$type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.typeColKey, row.getObjectKey(), true);
                return;
            }
            row.getTable().setString(columnInfo.typeColKey, row.getObjectKey(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.typeColKey);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.typeColKey, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder(NO_ALIAS, "Message", false, 5, 0);
        builder.addPersistedProperty(NO_ALIAS, "id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty(NO_ALIAS, "phoneNumber", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty(NO_ALIAS, "text", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty(NO_ALIAS, "date", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty(NO_ALIAS, "type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static MessageColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new MessageColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Message";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Message";
    }

    @SuppressWarnings("cast")
    public static com.example.sms_group3.Message createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.example.sms_group3.Message obj = null;
        if (update) {
            Table table = realm.getTable(com.example.sms_group3.Message.class);
            MessageColumnInfo columnInfo = (MessageColumnInfo) realm.getSchema().getColumnInfo(com.example.sms_group3.Message.class);
            long pkColumnKey = columnInfo.idColKey;
            long objKey = Table.NO_MATCH;
            if (!json.isNull("id")) {
                objKey = table.findFirstLong(pkColumnKey, json.getLong("id"));
            }
            if (objKey != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(objKey), realm.getSchema().getColumnInfo(com.example.sms_group3.Message.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_example_sms_group3_MessageRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.com_example_sms_group3_MessageRealmProxy) realm.createObjectInternal(com.example.sms_group3.Message.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_example_sms_group3_MessageRealmProxy) realm.createObjectInternal(com.example.sms_group3.Message.class, json.getLong("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }

        final com_example_sms_group3_MessageRealmProxyInterface objProxy = (com_example_sms_group3_MessageRealmProxyInterface) obj;
        if (json.has("phoneNumber")) {
            if (json.isNull("phoneNumber")) {
                objProxy.realmSet$phoneNumber(null);
            } else {
                objProxy.realmSet$phoneNumber((String) json.getString("phoneNumber"));
            }
        }
        if (json.has("text")) {
            if (json.isNull("text")) {
                objProxy.realmSet$text(null);
            } else {
                objProxy.realmSet$text((String) json.getString("text"));
            }
        }
        if (json.has("date")) {
            if (json.isNull("date")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'date' to null.");
            } else {
                objProxy.realmSet$date((long) json.getLong("date"));
            }
        }
        if (json.has("type")) {
            if (json.isNull("type")) {
                objProxy.realmSet$type(null);
            } else {
                objProxy.realmSet$type((String) json.getString("type"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.sms_group3.Message createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.example.sms_group3.Message obj = new com.example.sms_group3.Message();
        final com_example_sms_group3_MessageRealmProxyInterface objProxy = (com_example_sms_group3_MessageRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("phoneNumber")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$phoneNumber((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$phoneNumber(null);
                }
            } else if (name.equals("text")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$text((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$text(null);
                }
            } else if (name.equals("date")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$date((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'date' to null.");
                }
            } else if (name.equals("type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$type(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
        }
        return realm.copyToRealmOrUpdate(obj);
    }

    static com_example_sms_group3_MessageRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.example.sms_group3.Message.class), false, Collections.<String>emptyList());
        io.realm.com_example_sms_group3_MessageRealmProxy obj = new io.realm.com_example_sms_group3_MessageRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.example.sms_group3.Message copyOrUpdate(Realm realm, MessageColumnInfo columnInfo, com.example.sms_group3.Message object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.example.sms_group3.Message) cachedRealmObject;
        }

        com.example.sms_group3.Message realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.example.sms_group3.Message.class);
            long pkColumnKey = columnInfo.idColKey;
            long objKey = table.findFirstLong(pkColumnKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id());
            if (objKey == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(objKey), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_example_sms_group3_MessageRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.example.sms_group3.Message copy(Realm realm, MessageColumnInfo columnInfo, com.example.sms_group3.Message newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.sms_group3.Message) cachedRealmObject;
        }

        com_example_sms_group3_MessageRealmProxyInterface unmanagedSource = (com_example_sms_group3_MessageRealmProxyInterface) newObject;

        Table table = realm.getTable(com.example.sms_group3.Message.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);

        // Add all non-"object reference" fields
        builder.addInteger(columnInfo.idColKey, unmanagedSource.realmGet$id());
        builder.addString(columnInfo.phoneNumberColKey, unmanagedSource.realmGet$phoneNumber());
        builder.addString(columnInfo.textColKey, unmanagedSource.realmGet$text());
        builder.addInteger(columnInfo.dateColKey, unmanagedSource.realmGet$date());
        builder.addString(columnInfo.typeColKey, unmanagedSource.realmGet$type());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_example_sms_group3_MessageRealmProxy managedCopy = newProxyInstance(realm, row);
        cache.put(newObject, managedCopy);

        return managedCopy;
    }

    public static long insert(Realm realm, com.example.sms_group3.Message object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.example.sms_group3.Message.class);
        long tableNativePtr = table.getNativePtr();
        MessageColumnInfo columnInfo = (MessageColumnInfo) realm.getSchema().getColumnInfo(com.example.sms_group3.Message.class);
        long pkColumnKey = columnInfo.idColKey;
        long objKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            objKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id());
        }
        if (objKey == Table.NO_MATCH) {
            objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, objKey);
        String realmGet$phoneNumber = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$phoneNumber();
        if (realmGet$phoneNumber != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phoneNumberColKey, objKey, realmGet$phoneNumber, false);
        }
        String realmGet$text = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$text();
        if (realmGet$text != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.textColKey, objKey, realmGet$text, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.dateColKey, objKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$date(), false);
        String realmGet$type = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeColKey, objKey, realmGet$type, false);
        }
        return objKey;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.sms_group3.Message.class);
        long tableNativePtr = table.getNativePtr();
        MessageColumnInfo columnInfo = (MessageColumnInfo) realm.getSchema().getColumnInfo(com.example.sms_group3.Message.class);
        long pkColumnKey = columnInfo.idColKey;
        com.example.sms_group3.Message object = null;
        while (objects.hasNext()) {
            object = (com.example.sms_group3.Message) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long objKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                objKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id());
            }
            if (objKey == Table.NO_MATCH) {
                objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, objKey);
            String realmGet$phoneNumber = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$phoneNumber();
            if (realmGet$phoneNumber != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phoneNumberColKey, objKey, realmGet$phoneNumber, false);
            }
            String realmGet$text = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$text();
            if (realmGet$text != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.textColKey, objKey, realmGet$text, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.dateColKey, objKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$date(), false);
            String realmGet$type = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$type();
            if (realmGet$type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.typeColKey, objKey, realmGet$type, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.sms_group3.Message object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey();
        }
        Table table = realm.getTable(com.example.sms_group3.Message.class);
        long tableNativePtr = table.getNativePtr();
        MessageColumnInfo columnInfo = (MessageColumnInfo) realm.getSchema().getColumnInfo(com.example.sms_group3.Message.class);
        long pkColumnKey = columnInfo.idColKey;
        long objKey = Table.NO_MATCH;
        Object primaryKeyValue = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            objKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id());
        }
        if (objKey == Table.NO_MATCH) {
            objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, objKey);
        String realmGet$phoneNumber = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$phoneNumber();
        if (realmGet$phoneNumber != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.phoneNumberColKey, objKey, realmGet$phoneNumber, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.phoneNumberColKey, objKey, false);
        }
        String realmGet$text = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$text();
        if (realmGet$text != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.textColKey, objKey, realmGet$text, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.textColKey, objKey, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.dateColKey, objKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$date(), false);
        String realmGet$type = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeColKey, objKey, realmGet$type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.typeColKey, objKey, false);
        }
        return objKey;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.sms_group3.Message.class);
        long tableNativePtr = table.getNativePtr();
        MessageColumnInfo columnInfo = (MessageColumnInfo) realm.getSchema().getColumnInfo(com.example.sms_group3.Message.class);
        long pkColumnKey = columnInfo.idColKey;
        com.example.sms_group3.Message object = null;
        while (objects.hasNext()) {
            object = (com.example.sms_group3.Message) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && !RealmObject.isFrozen(object) && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getObjectKey());
                continue;
            }
            long objKey = Table.NO_MATCH;
            Object primaryKeyValue = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id();
            if (primaryKeyValue != null) {
                objKey = Table.nativeFindFirstInt(tableNativePtr, pkColumnKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id());
            }
            if (objKey == Table.NO_MATCH) {
                objKey = OsObject.createRowWithPrimaryKey(table, pkColumnKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$id());
            }
            cache.put(object, objKey);
            String realmGet$phoneNumber = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$phoneNumber();
            if (realmGet$phoneNumber != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.phoneNumberColKey, objKey, realmGet$phoneNumber, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.phoneNumberColKey, objKey, false);
            }
            String realmGet$text = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$text();
            if (realmGet$text != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.textColKey, objKey, realmGet$text, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.textColKey, objKey, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.dateColKey, objKey, ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$date(), false);
            String realmGet$type = ((com_example_sms_group3_MessageRealmProxyInterface) object).realmGet$type();
            if (realmGet$type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.typeColKey, objKey, realmGet$type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.typeColKey, objKey, false);
            }
        }
    }

    public static com.example.sms_group3.Message createDetachedCopy(com.example.sms_group3.Message realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.sms_group3.Message unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.sms_group3.Message();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.sms_group3.Message) cachedObject.object;
            }
            unmanagedObject = (com.example.sms_group3.Message) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_sms_group3_MessageRealmProxyInterface unmanagedCopy = (com_example_sms_group3_MessageRealmProxyInterface) unmanagedObject;
        com_example_sms_group3_MessageRealmProxyInterface realmSource = (com_example_sms_group3_MessageRealmProxyInterface) realmObject;
        Realm objectRealm = (Realm) ((RealmObjectProxy) realmObject).realmGet$proxyState().getRealm$realm();
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());
        unmanagedCopy.realmSet$phoneNumber(realmSource.realmGet$phoneNumber());
        unmanagedCopy.realmSet$text(realmSource.realmGet$text());
        unmanagedCopy.realmSet$date(realmSource.realmGet$date());
        unmanagedCopy.realmSet$type(realmSource.realmGet$type());

        return unmanagedObject;
    }

    static com.example.sms_group3.Message update(Realm realm, MessageColumnInfo columnInfo, com.example.sms_group3.Message realmObject, com.example.sms_group3.Message newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_example_sms_group3_MessageRealmProxyInterface realmObjectTarget = (com_example_sms_group3_MessageRealmProxyInterface) realmObject;
        com_example_sms_group3_MessageRealmProxyInterface realmObjectSource = (com_example_sms_group3_MessageRealmProxyInterface) newObject;
        Table table = realm.getTable(com.example.sms_group3.Message.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, flags);
        builder.addInteger(columnInfo.idColKey, realmObjectSource.realmGet$id());
        builder.addString(columnInfo.phoneNumberColKey, realmObjectSource.realmGet$phoneNumber());
        builder.addString(columnInfo.textColKey, realmObjectSource.realmGet$text());
        builder.addInteger(columnInfo.dateColKey, realmObjectSource.realmGet$date());
        builder.addString(columnInfo.typeColKey, realmObjectSource.realmGet$type());

        builder.updateExistingTopLevelObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Message = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{phoneNumber:");
        stringBuilder.append(realmGet$phoneNumber() != null ? realmGet$phoneNumber() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{text:");
        stringBuilder.append(realmGet$text() != null ? realmGet$text() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{date:");
        stringBuilder.append(realmGet$date());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{type:");
        stringBuilder.append(realmGet$type() != null ? realmGet$type() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long objKey = proxyState.getRow$realm().getObjectKey();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (objKey ^ (objKey >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com_example_sms_group3_MessageRealmProxy aMessage = (com_example_sms_group3_MessageRealmProxy)o;

        BaseRealm realm = proxyState.getRealm$realm();
        BaseRealm otherRealm = aMessage.proxyState.getRealm$realm();
        String path = realm.getPath();
        String otherPath = otherRealm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;
        if (realm.isFrozen() != otherRealm.isFrozen()) return false;
        if (!realm.sharedRealm.getVersionID().equals(otherRealm.sharedRealm.getVersionID())) {
            return false;
        }

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aMessage.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getObjectKey() != aMessage.proxyState.getRow$realm().getObjectKey()) return false;

        return true;
    }
}
