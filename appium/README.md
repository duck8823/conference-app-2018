# AWS Device Farm

## Build
```bash
$ cd ${PROJECT_ROOT}
$ ./gradlew app:assembleDebug
$ ./gradlew appium:createPomForDeviceFarm
$ mvn -f appium/pom.devicefarm.xml -DskipTests clean package 
```
## Upload app apk
```bash
aws devicefarm create-upload --project-arn "${PROJECT_ARN}" --name app-debug.apk --type ANDROID_APP
```
```json
{
    "upload": {
        "arn": "${APP_ARN}",
        "name": "app-debug.apk",
        "created": 1517096679.742,
        "type": "ANDROID_APP",
        "status": "INITIALIZED",
        "url": "${URL_FOR_APP_UPLOAD}"
    }
}
```

```bash
$ curl -T app/build/outputs/apk/debug/app-debug.apk ${URL_FOR_APP_UPLOAD}
```

## Upload appium test zip
```bash
$ aws devicefarm create-upload --project-arn "arn:aws:devicefarm:us-west-2:679711568397:project:a656f881-d8fb-4ac1-9a73-fe2d1b5d7e76" --name appium-java-from-cli.zip --type APPIUM_JAVA_JUNIT_TEST_PACKAGE
```
```json
{
    "upload": {
        "arn": "${APPIUM_TEST_ARN}",
        "name": "appium-java-from-cli.zip",
        "created": 1517097312.185,
        "type": "APPIUM_JAVA_JUNIT_TEST_PACKAGE",
        "status": "INITIALIZED",
        "url": "${URL_FOR_APPIUM_TEST_UPLOAD}"
    }
}
```
```bash
curl -T appium/target/zip-with-dependencies.zip "${URL_FOR_APPIUM_TEST_UPLOAD}"
```

## Test Schedule

```bash
$ aws devicefarm schedule-run --project-arn "${PROJECT_ARN}" --device-pool-arn "${DEVICE_POOL_ARN}" --test "type=APPIUM_JAVA_JUNIT,testPackageArn=${APPIUM_TEST_ARN}" --app-arn "${APP_ARN}"
```
```json
{
    "run": {
        "arn": "${TEST_RUN_ARN}",
        "name": "app-debug.apk",
        "type": "APPIUM_JAVA_JUNIT",
        "platform": "ANDROID_APP",
        "created": 1517101236.391,
        "status": "SCHEDULING",
        "result": "PENDING",
        "counters": {
            "total": 0,
            "passed": 0,
            "failed": 0,
            "warned": 0,
            "errored": 0,
            "stopped": 0,
            "skipped": 0
        },
        "totalJobs": 3,
        "completedJobs": 0,
        "billingMethod": "METERED",
        "appUpload": "${APP_ARN}",
        "jobTimeoutMinutes": 10,
        "devicePoolArn": "${DEVICE_POOL_ARN}",
        "radios": {
            "wifi": true,
            "bluetooth": true,
            "nfc": true,
            "gps": true
        }
    }
}

```


if you wont to confirm `device-pool-arn`, try following command.

```bash
$ aws devicefarm list-device-pools --arn "${PROJECT_ARN}"
```

returns 

```json
{
    "devicePools": [
        ...
        {
            "arn": "${DEVICE_POOL_ARN}",
            "name": "demo",
            "type": "PRIVATE",
            "rules": [
                {
                    "attribute": "ARN",
                    "operator": "IN",
                    "value": "[\"arn:aws:devicefarm:us-west-2::device:5F20BBED05F74D6288D51236B0FB9895\",\"arn:aws:devicefarm:us-west-2::device:6B26991B2257455788C5B8EA3C9F91C4\",\"arn:aws:devicefarm:us-west-2::device:72B1388DC6F941A293C00A8CBD557EF6\"]"
                }
            ]
        }
    ]
}
```
