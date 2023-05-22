export interface JobInfo {
    id: string,
    name: string,
    description: string,
    ownerId: string,
    status: string,
    startTime: string,
    createTime: string,
    cpuUsage: number,
    memUsage: number,
    jmMem: number,
    tmMem: number,
    parallelism: number,
    tmSlot: number,
    flinkUrl: string,
    monitorUrl: string,
    outerUrl: string,
    type: string,
    jarName: string,
    mainClass: string,
    mainArgs: string,
    userSql: string
}

export interface JobActionInfo {
    jobId: string,
    action: string,
    startType: string
}