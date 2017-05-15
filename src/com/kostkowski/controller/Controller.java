package com.kostkowski.controller;

import com.kostkowski.view.View;
import com.kostkowski.model.Shop;

import java.util.List;

/**
 * Created by Grzegorz Kostkowski on 2017-03-04.
 */
public class Controller {
    private Shop model;
    private View view;
    private boolean exit = false;

    public Controller(Shop model, View view) {
        this.model = model;
        this.view = view;
        setDataSource();
        if (!exit)
            run();
    }


    private void setDataSource() {
        view.printlnToScreen(view.DATA_CONTAINER_SETUP);
        view.printPromptSign();
        boolean selMade = false;
        while (!selMade && !exit) {
            String sel = view.readLine();
            if (!sel.equals(view.EXIT)) {
                selMade = model.setDataContainer(sel);
                view.printlnToScreen(selMade ? view.SETUP_SUCC : view.SETUP_FAIL + "\n" + view.PROMPT_MSG);
            } else
                exit = true;
        }

    }

    private void run() {
        view.initPrint();
        boolean exit = false;
        while (!exit) {
            view.printPromptSign();
            List<String> inputs = view.readAndSplitLine(true);
            assert !inputs.isEmpty() : "Empty command";
            Action cmd = recognizeAction(inputs.remove(0));
            if (cmd != null) {
                switch (cmd) {
                    case CREATE_SHELF:
                        view.printFeedback(
                                model.placeShelf(inputs),
                                view.ADD_SUCCESS_MSG,
                                view.INVALID_DATA_MSG);
                        break;
                    case RETRIEVE_SHELF:
                        Action reqested;
                        if (!inputs.isEmpty()) {
                            if ((reqested = recognizeAction(inputs.get(0))) != null && reqested.equals(Action.RETRIEVE_ALL)) {
                                view.printFeedbackEx(
                                        model.checkShelfs().toString(),
                                        view.INVALID_ID_MSG);

                            } else
                                view.printFeedbackEx(
                                        model.checkShelf(inputs),
                                        view.INVALID_ID_MSG);
                        } else view.printInvalidCmdWarn();
                        break;
                    case UPDATE_SHELF:
                        view.printFeedback(
                                model.rearrangeShelf(inputs),
                                view.UPDATE_SUCCESS_MSG,
                                view.INVALID_DATA_MSG);
                        break;
                    case DELETE_SHELF:
                        view.printFeedbackEx(
                                view.DELETE_SUCCESS_MSG + model.takeOutShelf(inputs),
                                view.INVALID_ID_MSG);
                        break;
                    case CREATE_PROD:
                        view.printFeedback(
                                model.putOnShelf(inputs),
                                view.ADD_SUCCESS_MSG,
                                view.INVALID_DATA_MSG);
                        break;
                    case RETRIEVE_PROD:
                        if (!inputs.isEmpty()) {
                            if ((reqested = recognizeAction(inputs.get(0))) != null && reqested.equals(Action.RETRIEVE_ALL)) //retrieve all
                                view.printFeedbackEx(
                                        model.checkProducts(),
                                        view.INVALID_ID_MSG);
                            else if (inputs.size() > 1 && (reqested = recognizeAction(inputs.get(1))) != null && reqested.equals(Action.RETRIEVE_ALL)) //retrieve for shelf
                                try {
                                    view.printFeedbackEx(
                                            model.checkProducts(Integer.parseInt(inputs.get(0))),
                                            view.INVALID_ID_MSG);
                                } catch (NumberFormatException e) {
                                    view.printInvalidCmdWarn();
                                }
                            else view.printFeedbackEx(
                                        model.checkProduct(inputs),
                                        view.INVALID_ID_MSG);
                        } else view.printInvalidCmdWarn();

                        break;
                    case UPDATE_PROD:
                        view.printFeedback(
                                model.changeProduct(inputs),
                                view.UPDATE_SUCCESS_MSG,
                                view.INVALID_DATA_MSG);
                        break;
                    case DELETE_PROD:
                        view.printFeedbackEx(
                                view.DELETE_SUCCESS_MSG + model.takeFromShelf(inputs),
                                view.INVALID_ID_MSG);
                        break;
                    case CREATE_WORKER:
                        view.printFeedback(
                                model.createWorker(inputs),
                                view.ADD_SUCCESS_MSG,
                                view.INVALID_DATA_MSG);
                        break;
                    case DELETE_WORKER:
                        view.printFeedbackEx(
                                view.DELETE_SUCCESS_MSG + model.deleteWorker(inputs),
                                view.INVALID_ID_MSG);
                        break;
                    case RETRIEVE_WORKER:
                        if (!inputs.isEmpty()) {
                            if ((reqested = recognizeAction(inputs.get(0))) != null && reqested.equals(Action.RETRIEVE_ALL)) {
                                view.printFeedbackEx(
                                        model.checkWorkers().toString(),
                                        view.INVALID_ID_MSG);

                            } else
                                view.printFeedbackEx(
                                        model.checkWorker(inputs),
                                        view.INVALID_ID_MSG);
                        } else view.printInvalidCmdWarn();
                        break;
                    case ASSIGN_WORKER:
                        view.printFeedback(
                                model.assignWorker(inputs),
                                view.ASSIGN_SUCCESS_MSG,
                                view.INVALID_DATA_MSG);

                        break;
                    case RETRACT_WORKER_BY_SALESMAN_ID:
                        view.printFeedback(
                                model.retractWorkerBySalesmanId(inputs),
                                view.RETRACT_SUCCESS_MSG,
                                view.INVALID_DATA_MSG);
                        break;
                    case RETRACT_WORKER_BY_STALL_ID:
                        view.printFeedback(
                                model.retractWorkerByStallId(inputs),
                                view.RETRACT_SUCCESS_MSG,
                                view.INVALID_DATA_MSG);
                        break;
                    case EXIT:
                        model.finishSession();
                        exit = true;
                        break;
                    case HELP:
                        view.printHelp(inputs.size() == 0 ? null : inputs.get(0));
                        break;
                    default:
                        view.printInvalidCmdWarn();
                }
            } else view.printInvalidCmdWarn();

        }
    }

    Action recognizeAction(String cmd) {
        return Action.getAction(cmd);
    }
}
