package com.mrlv.modeler;


import cn.hutool.core.io.IoUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mrlv.common.core.BaseController;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对流程模型的处理
 */
@RestController
@RequestMapping("/modeler")
public class ModelerController extends BaseController implements ModelDataJsonConstants {

    @Resource
    private RepositoryService repositoryService;


    @RequestMapping("/deployModel")
    public String deployModel(String modelId){
        try {
            //获取模型
            Model modelData = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
            String processName = modelData.getName() + ".bpmn20.xml";
            //将模型生成流程，生成后无法再进行改动
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
            logger.info("部署成功，部署ID=" + deployment.getId());
        } catch (IOException e) {
            logger.info("部署失败");
            e.printStackTrace();
        }
        return "200";
    }

    /**
     * 导出model的xml文件
     */
    @RequestMapping("/exportBpmn")
    public void exportBpmn(String modelId, HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(editorNode);
            // 流程非空判断
            if (!CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
                byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

                ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
                String filename = bpmnModel.getMainProcess().getId() + ".bpmn";
                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
                IoUtil.copy(in, response.getOutputStream());
                response.flushBuffer();
            }
        } catch (Exception e) {
            logger.error("导出model的xml文件失败：modelId={}", modelId, e);
        }
    }

    @RequestMapping("/createModel")
    public String createModel(String data) {
        try {
            String modelName = "modelName6";
            String modelKey = "modelKey66";
            String description = "modelKey666";

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(MODEL_NAME, modelName);
            modelObjectNode.put(MODEL_REVISION, 1);
            modelObjectNode.put(MODEL_DESCRIPTION, description);

            Model modelData = repositoryService.newModel();
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(modelName);
            modelData.setKey(modelKey);
            //保存模型
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            return modelData.getId();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("/modelList")
    public List<ModelEntityImpl> modelList(String data){
        ModelQuery modelQuery = repositoryService.createModelQuery();
        List<ModelEntityImpl> list = modelQuery.list().stream().map((e) -> (ModelEntityImpl)e).collect(Collectors.toList());
        return list;
    }

    @RequestMapping("/delModel")
    public String delModel(String modelId){
        repositoryService.deleteModel(modelId);
        return "ok";
    }
}
