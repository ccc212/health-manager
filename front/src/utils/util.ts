import { ElMessage } from 'element-plus';

export function handleResponse(response: any) {
    if (response.data.code !== 200) {
        ElMessage.error(response.data.msg);
    } else {
        // 检查 response.data.data 是否为字符串
        const message = typeof response.data.data === 'string' ? response.data.data : '操作成功';
        ElMessage.success(message);
    }
}
